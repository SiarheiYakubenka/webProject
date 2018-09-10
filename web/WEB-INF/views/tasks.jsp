<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF8">
    <title>Web project</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://malsup.github.com/jquery.form.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/app-ajax.js"></script>
</head>
<body>
<header>
    <%@include file="header.jsp"%>
</header>

<div id="cnt" class="container">
    <h2>Tasks</h2>
    <br>
    <ul class="nav nav-tabs" role="tablist">
        <li class="nav-item">
            <a id="today" class="nav-link active" data-toggle="tab" href="#today-content">Today</a>
        </li>
        <li class="nav-item">
            <a id="tomorrow" class="nav-link" data-toggle="tab" href="#tomorrow-content">Tomorrow</a>
        </li>
        <li class="nav-item">
            <a id="someday" class="nav-link" data-toggle="tab" href="#someday-content">Someday</a>
        </li>
        <li class="nav-item">
            <a id="fixed" class="nav-link" data-toggle="tab" href="#fixed-content">Fixed</a>
        </li>
        <li class="nav-item">
            <a id="trash" class="nav-link" data-toggle="tab" href="#trash-content">Recycle Bin</a>
        </li>
    </ul>

    <div class="tab-content">
        <div id="overdue" ></div>
        <div id="buttons-checkboxOverdue" style="display: none">
            <button id="fix-checkedOverdue"  class="btn " >Fix checked</button>
            <button id="del-checkedOverdue" class="btn " >Delete checked</button>
        </div>
        <div id="today-content" class="container tab-pane active"><br>
            <h3>
                <div class="row">
                    <div class="col">Today</div>
                    <div id="curDate" class="col"></div>
                </div>
            </h3>

        </div>
        <div id="tomorrow-content" class="container tab-pane fade"><br>
            <h3>
                <div class="row">
                    <div class="col">Tomorrow</div>
                    <div id="tomorrowDate" class="col"></div>
                </div>
            </h3>
        </div>
        <div id="someday-content" class="container tab-pane fade"><br>
            <h3>Someday</h3>
        </div>
        <div id="fixed-content" class="container tab-pane fade"><br>
            <h3>Fixed</h3>
        </div>
        <div id="trash-content" class="container tab-pane fade"><br>
            <h3>Recycle Bin</h3>
        </div>
    </div>
        <div class="tasks">
        </div>
    <div id="buttons-checkbox">
        <button id="del-all" style="display: none" class="btn " >Empty trash</button>
        <button id="fix-checked" style="display: none" class="btn " >Fix checked</button>
        <button id="del-checked" style="display: none" class="btn " >Delete checked</button>
        <button id="remove-checked" style="display: none" class="btn " >Remove checked</button>
        <button id="recover-checked" style="display: none" class="btn " >Recover checked</button>
    </div>
    <div id="addTask" style="display: none">
        <form class='addTask'><a class='add-link' href='#'>Add Task</a></form>
    </div>
    <div id="addTaskForm" style="display: none">
        <form id="add-form">
            <div class="row">
                <div class="col-sm-9">
                    <div class="form-group">
                        <label for="description">Description:</label>
                        <textarea class="form-control" id="description"  rows="1" autofocus></textarea>
                    </div>
                </div>
                <div  class="col-sm-3">
                    <div id="compDate" class="form-group" >
                        <label for="completionsDate">Date of completion:</label>
                        <input id="completionsDate" type="date"  class="form-control">
                    </div>
                </div>
            </div>
            <button id="add-button" type="button" class="btn btn-primary task">Add task</button>
            <button id="add-cancel" type='button' class='btn btn-primary'>Cancel</button>
        </form>
    </div>
</div>
<footer>
    <%@include file="footer.jsp"%>
</footer>
<!-- The Modal -->
<div class="modal fade" id="updateTask">
    <div class="modal-dialog modal-lg modal-dialog-centered">
        <div class="modal-content">

            <!-- Modal Header -->
            <div class="modal-header">
                <h4 class="modal-title">Updating</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>

            <!-- Modal body -->
            <div class="modal-body">
                <form id="update-form">
                    <div class="row">
                        <div class="col-sm-9">
                            <div class="form-group">
                                <label for="update-desc">Description:</label>
                                <textarea class="form-control" id="update-desc"  rows="1" autofocus></textarea>
                            </div>
                        </div>
                        <div  class="col-sm-3">
                            <div  class="form-group" >
                                <label for="upCompletionsDate">Date of completion:</label>
                                <input id="upCompletionsDate" type="date"  class="form-control">
                            </div>
                        </div>
                    </div>
                </form>
            </div>

            <!-- Modal footer -->
            <div class="modal-footer">
                <button id="update-button" type="button" class="btn btn-primary task" data-dismiss="modal">Update</button>
                <button id="update-cancel" type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
            </div>
        </div>
    </div>
</div>

<!-- The Modal -->
<div class="modal fade" id="deleteTask">
    <div class="modal-dialog modal-lg modal-dialog-centered">
        <div class="modal-content">

            <!-- Modal Header -->
            <div class="modal-header">
                <h4 class="modal-title">Deleting</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>

            <!-- Modal body -->
            <div class="modal-body">
                <div id="del-body" style= 'max-width: 600px; white-space:nowrap; overflow:hidden; text-overflow:ellipsis'></div>
            </div>

            <!-- Modal footer -->
            <div class="modal-footer">
                <button type="button" id="delete" class="btn btn-danger btn-task" data-dismiss="modal">Delete</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
            </div>

        </div>
    </div>
</div>
<!-- The Modal -->
<div class="modal fade" id="uploadFile">
    <div class="modal-dialog modal-lg modal-dialog-centered">
        <div class="modal-content">

            <!-- Modal Header -->
            <div class="modal-header">
                <h4 class="modal-title">Upload file</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <!-- Modal body -->
            <div class="modal-body">
                <form id="upload-form" class="upload-box">
                    <input type="hidden" id="idTask" name="idTask"/>
                    <label for="sampleFile">Please select a file</label>
                    <input id="sampleFile" name="sampleFile" type="file" /> <br/>

                    <input id="uploadBtn" type="button" class="btn btn-primary" data-dismiss="modal" value="Upload" />
                </form>
            </div>

            <!-- Modal footer -->
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
            </div>

        </div>
    </div>
</div>
<!-- Modal -->
<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="date-show"></h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <div id="desc-show"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>