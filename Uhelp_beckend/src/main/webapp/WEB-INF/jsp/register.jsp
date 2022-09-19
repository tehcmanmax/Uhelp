
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Refugee registration Form</title>
    <link rel="shortcut icon" href="#">
    <link href="css/custom.css" rel="stylesheet">
    <link href="css/main.css" rel="stylesheet">
</head>

<body>

<div class="form-container">

    <h1>Refugee registration Form</h1>
    <form:form method="POST" modelAttribute="user" commandName="user" class="form-horizontal" action="/register">


        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="tgUsername">tgUsername</label>
                <div class="col-md-7">
                    <form:input type="text" path="tg" id="tgUsername" class="form-control input-sm"/>
                    <div class="has-error">
                        <form:errors path="tgUsername" class="help-inline"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="name">Name</label>
                <div class="col-md-7">
                    <form:input type="text" path="name" id="name" class="form-control input-sm"/>
                    <div class="has-error">
                        <form:errors path="name" class="help-inline"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="phoneNumber">phone Number</label>
                <div class="col-md-7">
                    <form:input type="text" path="phoneNumber" id="phoneNumber" class="form-control input-sm"/>
                    <div class="has-error">
                        <form:errors path="phoneNumber" class="help-inline"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="email">Email</label>
                <div class="col-md-7">
                    <form:input type="text" path="email" id="email" class="form-control input-sm"/>
                    <div class="has-error">
                        <form:errors path="email" class="help-inline"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="social">social</label>
                <div class="col-md-7">
                    <form:input type="text" path="social" id="social" class="form-control input-sm"/>
                    <div class="has-error">
                        <form:errors path="social" class="help-inline"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="age">age</label>
                <div class="col-md-7">
                    <form:input type="text" path="age" id="age" class="form-control input-sm"/>
                    <div class="has-error">
                        <form:errors path="age" class="help-inline"/>
                    </div>
                </div>
            </div>
        </div>


        <div class="row">
            <div class="form-group col-md-12">
                <%--@declare id="status"--%><label class="col-md-3 control-lable" for="status">status</label>
                <div class="col-md-7" class="form-control input-sm">
                    <form:radiobutton path="status" value="Host"/>Host
                    <form:radiobutton path="status" value="Refugee"/>Refugee
                    <div class="has-error">
                        <form:errors path="status" class="help-inline"/>
                    </div>
                </div>
            </div>
        </div>


        <div class="row">
            <div class="form-group col-md-12">
                <%--@declare id="sex"--%><label class="col-md-3 control-lable" for="sex">Sex</label>
                <div class="col-md-7" class="form-control input-sm">
                    <form:radiobutton path="sex" value="M"/>Male
                    <form:radiobutton path="sex" value="F"/>Female
                    <div class="has-error">
                        <form:errors path="sex" class="help-inline"/>
                    </div>
                </div>
            </div>
        </div>


        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="city">city</label>
                <div class="col-md-7">
                    <form:input type="text" path="city" id="city" class="form-control input-sm"/>
                    <div class="has-error">
                        <form:errors path="city" class="help-inline"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="country">country</label>
                <div class="col-md-7">
                    <form:input type="text" path="country" id="country" class="form-control input-sm"/>
                    <div class="has-error">
                        <form:errors path="country" class="help-inline"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="amountOfPeople">amountOfPeople</label>
                <div class="col-md-7">
                    <form:input type="text" path="amountOfPeople" id="amountOfPeople" class="form-control input-sm"/>
                    <div class="has-error">
                        <form:errors path="amountOfPeople" class="help-inline"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="date">Date of arrival(Or leave empty</label>
                <div class="col-md-7">
                    <form:input type="text" path="date" id="date" class="form-control input-sm"/>(yyyy-MM-dd)
                    <div class="has-error">
                        <form:errors path="dob" class="help-inline"/>
                    </div>
                </div>
            </div>
        </div>


        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-lable" for="additional">additional</label>
                <div class="col-md-7">
                    <form:input type="text" path="additional" id="additional" class="form-control input-sm"/>
                    <div class="has-error">
                        <form:errors path="additional" class="help-inline"/>
                    </div>
                </div>
            </div>
        </div>



        <div class="row">
            <div class="form-actions floatRight">
                <input type="submit" value="Register" class="btn btn-primary btn-sm">
            </div>
        </div>
    </form:form>
</div>
</body>
</html>