#ifndef _UITILITY_CONSOLE_READER_
#define _UITILITY_CONSOLE_READER_



#include <iostream>
#include <string>
#include <sstream>
#include <vector>
#include <limits>
#include <exception>
#include "reader.cpp"

using std::vector;
using std::getline;
using std::cin;
using std::string;
using std::istream;
using std::stringstream;
using std::exception;

class Console_reader: public Reader {
    vector<string> mlines;
    vector<string> mwords;

public:
    Console_reader(){}

    
    /*
      args: {:}
      return value: the reference of istream
      Usage: use like the following example
      while (next_word(str)) {
         cout << s << " ";
      }
    */
    istream& next_word(string& s) {
        cin >> s;
        if (cin) {
            mwords.push_back(s);
        }
        return cin;
    }
    istream& readwords() {
        string s;
        while (next_word(s)){
        }
        return cin;
    }
    istream& next_int(int& i){
        string s;
        cin >> s;
        if (cin){
            i =  std::stoi(s);
        }
        return cin;
    }
    istream& next_hex(int &i) {
        cin >> std::hex >> i;
        return cin;
    }
    istream& next_long(long& i) {
        string s;
        cin >> s;
        if (cin) {
            i = std::stol(s);
        }
        return cin;
    }
    istream& next_double(double& d){
        string s;
        cin >> s;
        if (cin){
            d = std::stod(s);
        }
        return cin;
    }
    istream& next_line(string& s) {
        getline(cin, s);
        if (cin) {
            mlines.push_back(s);
        }
        return cin;
    }
    istream& readlines() {
        string s;
        while (next_line(s)) {
        }
        return cin;
    }
    
    
    vector<string> lines() const{
        return mlines;
    }
    vector<string> words() const{
        return mwords;
    }

    void reset () {
        cin.clear();
        //    cin.ignore();
    }
    void clear_words(){
        mwords.clear();
    }
    void clear_lines(){
        mlines.clear();
    }

    
    /*
      args: {:}
      return value:
      Usage: @deprecated: to use input_handler.split
    */
    vector<string> &split(const string &s, char delim, vector<string> &elems) {
        stringstream ss(s);
        string item;
        while (getline(ss, item, delim)) {
            elems.push_back(item);
        }
        return elems;
    }

    vector<string> split(const string &s, char delim) {
        vector<string> elems;
        split(s, delim, elems);
        return elems;
    }

    vector<string> split(const string &s) {
        stringstream ss(s);
        string item;
        vector<string> elems;
        while (ss >> item) {
            elems.push_back(item);
        }
        return elems;
    }

    void skip_line(int line) {
        for(int i = 0; i < line; i++) {
            cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
        }
        reset();
    }

    void skip_char(int i, char c = '\n') {
        if (i < 0) {
            throw exception();
        }
        cin.ignore(i, c);
        reset();
    }

};

#endif /* _UITILITY_CONSOLE_READER_ */