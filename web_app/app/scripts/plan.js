/**
 * Created by zzt on 11/18/15.
 */

var idCount = function () {
    var count = 1;
    return {
        getIdCount: function () {
            return count;
        },

        incrementAndGet: function () {
            count ++;
            return count;
        }
    }
}();

function addPlan(link) {
    'use strict';
    function setCaption (table, i) {
        var caption = table.find('caption');
        var text = caption.text();
        var indexOf = text.indexOf(i - 1 + '');
        caption.text(text.substr(0, indexOf) + i + text.substr(indexOf + 1));
    }
    // append a new table
    var table = $(link).parents('table');
    var id = table.attr('id');
    var nTable = table.clone()
        .attr('id', id.substr(0, id.length - 1) + idCount.incrementAndGet());
    table.after(nTable[0].outerHTML);
    // update caption of following table
    var captionNum = parseInt(table.find('caption').text().replace(/\D/g, ''));
    var i = captionNum + 1;
    var next = table;
    while ((next = next.next('table[id^="plan"]')).is('table')) {
        setCaption(next, i);
        i ++;
    }
}

function enableEdit(link) {
    'use strict';
    var table = $(link).parents('table');
    var tds = table.find('td');
    tds.attr('contenteditable', 'true').toggleClass('editable');
    tds[0].focus();
    $(link).hide();
    $(link).next().show();
}

function submitUpdate(link) {
    'use strict';
    var table = $(link).parents('table');
    // make it can't edit
    var tds = table.find('td');
    tds.attr('contenteditable', 'false').removeClass('editable');
    // TODO submit changes to server

    $(link).hide();
    $(link).prev().show();
}