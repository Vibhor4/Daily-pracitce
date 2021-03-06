#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <math.h>
#include "prime.h"

int size;
long long n;

// following two version has no synchronization, which
// not affect the correctness, but will make some duplicate check
// whether the lock will increase the throughput need to compare
// with find_prime_locked();
void *find_prime(void *arg) {
    arg_type *tmp = (arg_type *)arg;
    int start = tmp->start;
    int num_cpus = tmp->num_cpus;
    int *has = tmp->has;
    int i, j;
    //printf("s%d ", start);
    for (i = start; i < size; i+=num_cpus) {
        if (has[i] == MAY_BE_PRIME) {
            for (j = i * i; j < n; j+=i) {
                //printf("d%d ", j);
                has[j] = NOT_PRIME;
            }
        }
    }
    return NULL;
}

void *find_prime2(void *arg) {
        arg_type *tmp = (arg_type *)arg;
    int start = tmp->start;
    int gap = start - START;
    int num_cpus = tmp->num_cpus;
    int *has = tmp->has;
    int i, j;
    for (i = START; i < size; i++) {
        if (has[i] == MAY_BE_PRIME) {
            for (j = i * i + gap * i; j < n; j = j + i * num_cpus) {
                //printf("d%d ", j);
                has[j] = NOT_PRIME;
            }
        }
    }
    return NULL;
}

pthread_mutex_t work_mutex =
    PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t cond =
    PTHREAD_COND_INITIALIZER;
long long reach_count = 0;
void wait_or_notify(int num_cpus) {
    /**
       Failed trial to simulate cyclic barrier:
       if the fourth thread run immediately to
       reach here again, other thread will not wake,
       cause deadlock

       pthread_mutex_lock(&work_mutex);
       reach_count++;
       if (reach_count != num_cpus) {
       while (reach_count != num_cpus || reach_count != 0) {
       pthread_cond_wait(&cond, &work_mutex);
       }
       } else {
       pthread_cond_broadcast(&cond);
       reach_count = 0;
       }
       pthread_mutex_unlock(&work_mutex);
    */
    pthread_mutex_lock(&work_mutex);
    reach_count++;
    if (reach_count != num_cpus) {
        if (reach_count != num_cpus || reach_count != 0) {
            pthread_cond_wait(&cond, &work_mutex);
        }
    } else {
        pthread_cond_broadcast(&cond);
        reach_count = 0;
    }
    pthread_mutex_unlock(&work_mutex);

}

void *find_prime_locked(void *arg) {
    arg_type *tmp = (arg_type *)arg;
    int gap = tmp->start - START;
    int num_cpus = tmp->num_cpus;
    int *has = tmp->has;
    int i, j;
    for (i = START; i < size; i++) {
        if (has[i] == MAY_BE_PRIME) {
            for (j = i * i + gap * i; j < n; j = j + i * num_cpus) {
                //printf("d%d ", j);
                has[j] = NOT_PRIME;
            }
            wait_or_notify(num_cpus);
        }
    }
    return NULL;
}

int is_prime(int aim) {
    if ((aim & 1) == 0) {
        return false;
    }
    int i;
    int limit = sqrt(aim) + 1;
    for (i = 3; i < limit; i+=2) {
        if (aim % i == 0) {
            return false;
        }
    }
    return true;
}

void *another_algo(void *arg) {
    arg_type *tmp = (arg_type *)arg;
    int start = tmp->start;
    int num_cpus = tmp->num_cpus;
    int *has = tmp->has;
    int i;
    for (i = start + 2; i < n; i+=num_cpus) {
        if (!is_prime(i)) {
            has[i] = NOT_PRIME;
        }
    }
    return NULL;
}

void prime_pthread() {
    int res;
    size_t mem = sizeof(int)*(n + 1);
    int *has = malloc(mem);
    memset(has, MAY_BE_PRIME, mem);

    int num_cpus = sysconf(_SC_NPROCESSORS_ONLN);
    int i;
    pthread_t a_thread[num_cpus];
    arg_type *arg = calloc(num_cpus, sizeof(arg_type));

    Thread_Funcition thread_f = find_prime2;

    for (i = 1; i < num_cpus; i++) {
        arg[i].has = has;
        arg[i].num_cpus = num_cpus;
        arg[i].start = i + START;
        // argument will be sent to thread directly,
        // so use malloc or static
        res = pthread_create(&a_thread[i], NULL, thread_f, arg+i);
        if (res != 0) {
            perror("");
            exit(EXIT_FAILURE);
        }
    }
    i = 0;
    arg[i].has = has;
    arg[i].num_cpus = num_cpus;
    arg[i].start = i + START;
    thread_f(arg+i);

    for (i = 1; i < num_cpus; i++) {
        pthread_join(a_thread[i], NULL);
    }
    //output(has, n);
    free(has);
}

void set_argu_prime(long long num) {
    n = num;
    size = sqrt(n) + 1;
    prime_pthread();
}

//int main(int argc, char *argv[]){
//    if (argc <= 1) {
//        puts("Usage: prime N(upper bounds)");
//        return 0;
//    }
//    set_argu_prime(atoll(argv[1]));
//      return 0;
//}