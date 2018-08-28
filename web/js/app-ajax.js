$(document).ready(function(){
    var container = $(".container");
    container.on("click", ".nav-link", function () {
        var kindTask = this.id;
        $.ajax({
            method: 'get',
            url: 'app',
            data: 'kind=' + kindTask,
            cache: false,
            success: function (data) {
                hideCheckboxButtons();
                $('#del-all').hide();
                $('#buttons-checkboxOverdue').hide();
                $('#overdue').empty();
                showAddButton();
                    switch (kindTask) {
                        case 'today' :
                            var today = 0;
                            $('#curDate').html(getDate(today));
                            showTodayAndOverdue(data);
                            break;
                        case 'tomorrow' :
                            var tomorrow = 1;
                            $('#tomorrowDate').html(getDate(tomorrow));
                            showTomorrowTasks(data);
                            break;
                        case 'someday' :
                            showSomedayTasks(data);
                            break;
                        case 'fixed' :
                            showFixedTasks(data);
                            hideAddButton();
                            break;
                        case 'trash' :
                            showTrashTasks(data);
                            hideAddButton();
                            break;
                        default :
                            showMessage();
                    }
            },
            error: function (e) {
                alert('Error' + e);
            }
        });
    });

    document.getElementById('today').click();

    function setDefaultDate(day) {
        var dt = $('#completionsDate');
        dt.attr('min', sendDateFormat(getDate(day - 1)));
        dt.attr('value', sendDateFormat(getDate(day + 1)));
        $('#compDate').show();
    }

    function showAddButton(){
        $('#addTask').show();
        $('#addTaskForm').hide();
    }

    function hideAddButton() {
        $('#addTask').hide();
        $('#addTaskForm').hide();
    }

    function getDate(day) {
        var dt = new Date();
        dt.setDate(dt.getDate() + day);
        return dateFormat(dt);
    }

    function dateFormat(date) {
        var options = {
            year: 'numeric',
            month: 'short',
            day: 'numeric',
            weekday: 'long'
        };
        return date.toLocaleDateString("en-GB", options);
    }

    function sendDateFormat(date) {
        var d = new Date(date),
            month = '' + (d.getMonth() + 1),
            day = '' + d.getDate(),
            year = d.getFullYear();
        if (month.length < 2) month = '0' + month;
        if (day.length < 2) day = '0' + day;
        return [year, month, day].join('-');
    }

    function strToDate(date) {
        var dt = new Date();
        dt.setTime(Date.parse(date));
        return dt;
    }

    function showTodayAndOverdue(data) {
        if (data.length > 0) {
            var list = getTableHeader();
            var overdueTasks = "<br/><div class='alert alert-danger'><h3>Overdue</h3></div><table class='table'>" +
                "<tr><td colspan='6'><input id='checkAllOverdue' type='checkbox'/></td></tr>";
            var date;
            var countOverdue = 0;
            for (var i = 0; i < data.length; i++) {
                date = strToDate(data[i].completionsDate);
                var now = new Date();
                var today = new Date(now.getFullYear(), now.getMonth(), now.getDate());
                if (date < today) {
                    overdueTasks += "<tr id='"+  data[i].id + "' ><td><input  type='checkbox'/></td><td>"
                        + data[i].description + "</td><td>" +
                        dateFormat(date) + "</td><td>" +
                        getButton(' Fix ', 'fix') + "</td><td>" +
                        getButtonMod('#updateTask', 'Update', 'update') + "</td><td>" +
                        getButtonMod('#deleteTask', 'Delete', 'delete-task') + "</td></tr>";
                    countOverdue++;
                } else {
                    var file = getButtonMod('#uploadFile', 'Add File', 'upload-file');
                    if (data[i].fileName != null) {
                        file = getDropBox(data[i].fileName);
                    }
                    list += "<tr id='"+  data[i].id + "' ><td><input  type='checkbox'/></td><td>"
                        + data[i].description + "</td><td style='display: none'>" +
                        dateFormat(strToDate(data[i].completionsDate)) + "</td><td>" +
                        getButton(' Fix ', 'fix') + "</td><td>" +
                        getButtonMod('#updateTask', 'Update', 'update') + "</td><td>" +
                        getButtonMod('#deleteTask', 'Delete', 'delete-task') + "</td><td>" +
                        file + "</td></tr>";
                }
            }
            list += "</table>";
            overdueTasks += "</table>";
            $('.tasks').html(list);
            if (countOverdue > 0) {
                $('#overdue').html(overdueTasks);
                if (data.length === countOverdue) {
                    showMessage();
                }
            }
        } else {
            showMessage();
        }
    }


    function showTomorrowTasks(data) {
        if (data.length > 0) {
            var list = getTableHeader();
            for (var i = 0; i < data.length; i++) {
                var file = getButtonMod('#uploadFile', 'Add File', 'upload-file');
                if (data[i].fileName != null) {

                    file = getDropBox(data[i].fileName);
                }
                list += "<tr id='"+  data[i].id + "' ><td><input type='checkbox'/></td><td>"
                    + data[i].description + "</td><td style='display: none'>" +
                    dateFormat(strToDate(data[i].completionsDate)) + "</td><td>" +
                    getButton(' Fix ', 'fix') + "</td><td>" +
                    getButtonMod('#updateTask', 'Update', 'update') + "</td><td>" +
                    getButtonMod('#deleteTask', 'Delete', 'delete-task') + "</td><td>" +
                    file + "</td></tr>";
            }
            list += "</table>";
            $('.tasks').html(list);
        } else {
            showMessage();
        }
    }

    function showSomedayTasks(data) {
        if (data.length > 0) {
            var list = getTableHeader();
            for (var i = 0; i < data.length; i++) {
                var file = getButtonMod('#uploadFile', 'Add File', 'upload-file');
                if (data[i].fileName != null) {

                    file = getDropBox(data[i].fileName);
                }
                list += "<tr id='"+  data[i].id + "' ><td><input type='checkbox'/></td><td>"
                    + data[i].description + "</td><td>" +
                    dateFormat(strToDate(data[i].completionsDate)) + "</td><td>" +
                    getButton(' Fix ', 'fix') + "</td><td>" +
                    getButtonMod('#updateTask', 'Update', 'update') + "</td><td>" +
                    getButtonMod('#deleteTask', 'Delete', 'delete-task')+ "</td><td>" +
                    file+ "</td></tr>";
            }
            list += "</table>";
            $('.tasks').html(list);
        } else {
            showMessage();
        }
    }

    function showFixedTasks(data) {
        if (data.length > 0) {
            var list = getTableHeader();
            for (var i = 0; i < data.length; i++) {
                list += "<tr id='"+  data[i].id + "' ><td><input type='checkbox'/></td><td>"
                    + data[i].description + "</td><td>" +
                    dateFormat(strToDate(data[i].completionsDate)) + "</td><td>" +
                    getButtonMod('#deleteTask', 'Delete', 'delete-task')+ "</td></tr>";
            }
            list += "</table>";
            $('.tasks').html(list);
        } else {
            showMessage();
        }
    }

    function showTrashTasks(data) {
        if (data.length > 0) {
            $('#del-all').show();
            var list = getTableHeader();
            for (var i = 0; i < data.length; i++) {
                list += "<tr id='"+  data[i].id + "' ><td><input type='checkbox'/></td><td>"
                    + data[i].description + "</td><td>" +
                    dateFormat(strToDate(data[i].completionsDate)) + "</td><td>" +
                    getButton('Recover', 'recover') + "</td><td>" +
                    getButtonMod('#deleteTask', 'Remove', 'delete-task')+ "</td></tr>";
        }
        list += "</table>";
        $('.tasks').html(list);
        } else {
            showMessage();
        }
    }

    function showMessage() {
        var message = "<p class='alert alert-warning'>Tasks is zero</p>";
        $('.tasks').html(message);
    }

    function getTableHeader() {
        return "<table class='table'><tr><td colspan='6'><input id='checkAll' type='checkbox'/></td></tr>";
    }

    function getTableRow(data) {
        return "<tr><td><input id='"+  data[i].id +"' type='checkbox'/></td><td>" + data[i].description + "</td></tr>";

    }

    function getTableRowWithDate(data) {
        return "<tr><td><input id='"+  data[i].id + "' type='checkbox'/></td><td>" + data[i].description + "</td><td>" +
            dateFormat(strToDate(data)) + "</td></tr>";

    }
    
    function getButtonMod(myModal, value, clazz) {
    return  "<button type='button' class='btn btn-primary " + clazz + "' data-toggle='modal' data-target='" + myModal + "'>" +
           value + "</button>";
    }

    function getButton(value, clazz) {
        return  "<button type='button' class='btn btn-primary " + clazz + "' >" + value + "</button>";
    }


    $('.add-link').click(function () {
        if (getKindTask() === 'today' || getKindTask() === 'tomorrow') {
            $('#compDate').hide();
        } else if (getKindTask() === 'someday') {
            var tomorrow = 1;
            setDefaultDate(tomorrow);
        }
        $('#addTask').hide();
        $('#addTaskForm').show();
        $('#add-form')[0].reset();
    });

    $('#add-cancel').click(function () {
        $('#addTask').show();
        $('#addTaskForm').hide();
        $('#add-form')[0].reset();
    });


    $('#add-button').click(function(){
        var kindTask = getKindTask();
        var command = 'add';
        var des = $('#description').val();
        $('#addTask').show();
        $('#addTaskForm').hide();
        var now = new Date();
        var dt;
        switch (kindTask) {
            case 'today' :
                dt = new Date(now.getFullYear(), now.getMonth(), now.getDate());
                break;
            case 'tomorrow' :
                dt = new Date(now.getFullYear(), now.getMonth(), now.getDate() + 1);
                break;
            case 'someday' :
                dt = $('#completionsDate').val();
                break;
        }
        $('#add-form')[0].reset();
        dt = sendDateFormat(dt);
        var prop = 'command=' + command + '&description=' + des + '&completionsDate=' + dt;
        ajaxPost(prop);
    });

    container.on('click', '.fix', function () {
        var parentNode = $(this).parent().parent();
        var idTask = parentNode.attr('id');
        var prop = 'command=fix&id=' + idTask;
        ajaxPost(prop);
    });
    container.on('click', '.recover', function () {
        var parentNode = $(this).parent().parent();
        var idTask = parentNode.attr('id');
        var prop = 'command=recover&id=' + idTask;
        ajaxPost(prop);
    });

    container.on('click', '.update', function () {
        showAddButton();
        $('#update-form')[0].reset();
        var parentNode = $(this).parent().parent();
        var idTask = parentNode.attr('id');
        $('#update-button').attr('id-task', idTask);
        var task = parentNode.children().eq(1);
        var date = parentNode.children().eq(2);
        var dt = $('#upCompletionsDate');
        dt.attr('min', sendDateFormat(getDate(0)));
        dt.attr('value', sendDateFormat(strToDate(date.text())));
        $('textarea#update-desc').text(task.text());

    })

    container.on('click', '.upload-file', function () {
        showAddButton();
        var parentNode = $(this).parent().parent();
        var idTask = parentNode.prop('id');
        $('#idTask').prop('value', idTask);
    });

    $('#uploadBtn').click(function () {
        var idTask = document.getElementById("idTask").value;
        var sampleFile = document.getElementById("sampleFile").files[0];

        var formdata = new FormData();
        formdata.append("idTask", idTask);
        formdata.append("sampleFile", sampleFile);

        var xhr = new XMLHttpRequest();

        xhr.open("POST","/upload", true);
        xhr.send(formdata);

        xhr.onload = function() {
            alert(this.responseText);
            if (this.status === 200) {
                document.getElementById(getKindTask()).click();
            }
        };
    });


    $('#update-button').click(function () {
        var cmd = 'update';
        var des = $('#update-desc').val();
        var dt = $('#upCompletionsDate').val();
        var idTask = $('#update-button').attr('id-task');
        var prop = 'command=' + cmd + '&id=' + idTask + '&description=' + des + '&completionsDate=' + dt;
        ajaxPost(prop);
    });

    container.on('click', '.delete-task', function () {
        var parentNode = $(this).parent().parent();
        var idTask = parentNode.attr('id');
        var cmd = $(this).text();
        var task = parentNode.children().eq(1);
        var delBtn = $('#delete');
        $('#del-body').text("Are you sure you want to delete \"" + task.text() + "\" ?");
        delBtn.prop('id-task', idTask);
        delBtn.prop('cmd', cmd);

    });

    $('#delete').click(function () {
        var idTask = $(this).prop('id-task');
        var cmd = $(this).prop('cmd');
        var prop = 'command=' + cmd + '&id=' + idTask;
        ajaxPost(prop);
    });

    function ajaxPost(prop) {
        var kindTask = getKindTask();
        $.ajax({
            method: 'post',
            url: 'app',
            data: prop,
            success: function (success) {
                if ( success[0].ok) {
                    document.getElementById(kindTask).click();
                }else {
                    alert('Error database');
                }
            },
            error: function (e) {
                alert('Error' + e);
            }
        });
    }

    function getKindTask() {
        return $('a.nav-link.active').attr('id');
    }

    container.on('change', '.tasks input:checkbox', function() {
        var checkboxes = $('.tasks input:checkbox');
        var btns  = [];
        var delBtn = $('#del-checked');
        switch (getKindTask()) {
            case 'fixed' :
                btns.push(delBtn);
                break;
            case 'trash' :
                btns.push($('#recover-checked'), $('#remove-checked'));
                break;
            default :
                btns.push(($('#fix-checked')), delBtn);
        }
        showAndHideCheckbuttons(btns, checkboxes);
    });
    container.on('change', '#overdue input:checkbox', function() {
        var checkboxes = $('#overdue input:checkbox');
        var btns  = [$('#buttons-checkboxOverdue')];
        showAndHideCheckbuttons(btns, checkboxes);
    });

    function showAndHideCheckbuttons(btns, checkboxes) {
        var count = 0;
        checkboxes.each(function () {
            if (this.checked) {
                count++;
            }
        });
        if (count >= 1) {
            for (var i = 0; btns.length; i++) {
                btns[i].show();
            }
        } else {
            hideCheckboxButtons();
        }
    }

    function hideCheckboxButtons() {
        $('#fix-checked').hide();
        $('#del-checked').hide();
        $('#buttons-checkboxOverdue').hide();
        $('#recover-checked').hide();
        $('#remove-checked').hide();

    }

    container.on('click', '#checkAll', function () {
        $('.tasks input:checkbox').not(this).prop('checked', this.checked);
    });
    container.on('click', '#checkAllOverdue', function () {
        $('#overdue input:checkbox').not(this).prop('checked', this.checked);
    });

    container.on('click', '#fix-checkedOverdue', function (e) {
        var checkboxes = $("#overdue input:checked");
        actionChecked('fix', checkboxes);
        e.preventDefault();
    });

    container.on('click', '#fix-checked', function (e) {
        var checkboxes = $(".tasks input:checked");
        actionChecked('fix', checkboxes);
        e.preventDefault();
    });

    container.on('click', '#del-checkedOverdue', function (e) {
        var checkboxes = $("#overdue input:checked");
        actionChecked('delete', checkboxes);
        e.preventDefault();
    });

    container.on('click', '#del-checked', function (e) {
        var checkboxes = $(".tasks input:checked");
        actionChecked('delete', checkboxes);
        e.preventDefault();
    });

    container.on('click', '#remove-checked', function (e) {
        var checkboxes = $(".tasks input:checked");
        actionChecked('remove', checkboxes);
        e.preventDefault();
    });

    container.on('click', '#recover-checked', function (e) {
        var checkboxes = $(".tasks input:checked");
        actionChecked('recover', checkboxes);
        e.preventDefault();
    });

    function actionChecked(cmd, checkboxes) {
        var tasks = [];
        checkboxes.each(function() {
            var taskId = $(this).parent().parent().attr('id');
            if (taskId) {
                tasks.push(taskId);
            }
        });
        var pr = 'command=' + cmd + "&id=" + tasks;
        ajaxPost(pr);
    }

    $('#del-all').click(function (e) {
        var checkboxes = $('.tasks input:checkbox');
        actionChecked('remove', checkboxes);
        e.preventDefault();
    });

    function getDropBox(value) {
        return "<div class='btn-group'>" +
            "<button type='button' class='btn btn-primary'>" + value + "</button>" +
            "<button type='button' class='btn btn-primary dropdown-toggle dropdown-toggle-split'" +
        "data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'><span class='sr-only'>Toggle Dropdown</span>" +
        "</button><div class='dropdown-menu'><a class='dropdown-item' href='#'>Download</a><a class='dropdown-item' href='#'>Delete</a>" +
        "</div>"
    }

});