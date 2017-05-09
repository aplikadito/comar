<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, shrink-to-fit=no, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Comar - Login</title>

        <link
            href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
            crossorigin="anonymous">
        <link href="resources/css/login.css" rel="stylesheet">
    </head>

    <body>

        <div class="container">
            <div class="row">
                <div class="col-md-6 col-md-offset-3">
                    <div class="panel panel-login">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-xs-6">
                                    <a href="#" class="active" id="login-form-link">Ingreso</a>
                                </div>
                                <div class="col-xs-6">
                                    <a href="#" id="register-form-link">Registro</a>
                                </div>
                            </div>
                            <hr>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-12">
                                    
                                    
                                    <!--<form id="login-form" action="http://phpoll.com/login/process" method="post" role="form" style="display: block;">-->
                                    <form id="login-form">
                                        <div class="form-group">
                                            <input type="text" name="username" id="username" tabindex="1" class="form-control" placeholder="Usuario" value="">
                                        </div>
                                        <div class="form-group">
                                            <input type="password" name="password" id="password" tabindex="2" class="form-control" placeholder="Password">
                                        </div>
                                        <div class="form-group text-center">
                                            <input type="checkbox" tabindex="3" class="" name="remember" id="remember">
                                            <label for="remember">Recordar</label>
                                        </div>
                                        <div class="form-group">
                                            <div class="row">
                                                <div class="col-sm-6 col-sm-offset-3">
                                                    <!--<input type="submit" name="login-submit" id="login-submit" tabindex="4" class="form-control btn btn-login" value="Ingresar">-->
                                                    <input type="button" name="login-submit" id="login-submit" tabindex="4" class="form-control btn btn-login" value="Ingresar">
                                                </div>
                                            </div>
                                        </div>
<!--                                        <div class="form-group">
                                            <div class="row">
                                                <div class="col-lg-12">
                                                    <div class="text-center">
                                                        <a href="http://phpoll.com/recover" tabindex="5" class="forgot-password">Forgot Password?</a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>-->
                                    </form>
                                    
                                    <!--<form id="register-form" action="http://phpoll.com/register/process" method="post" role="form" style="display: none;">-->
                                    <form id="register-form" role="form" style="display: none;">
                                        <div class="form-group">
                                            <input type="text" name="username" id="username" tabindex="1" class="form-control" placeholder="Usuario" value="">
                                        </div>
                                        <div class="form-group">
                                            <input type="email" name="email" id="email" tabindex="1" class="form-control" placeholder="Email" value="">
                                        </div>
                                        <div class="form-group">
                                            <input type="password" name="password" id="password" tabindex="2" class="form-control" placeholder="Password">
                                        </div>
                                        <div class="form-group">
                                            <input type="password" name="confirm-password" id="confirm-password" tabindex="2" class="form-control" placeholder="Confirmar Password">
                                        </div>
                                        <div class="form-group">
                                            <div class="row">
                                                <div class="col-sm-6 col-sm-offset-3">
                                                    <!--<input type="submit" name="register-submit" id="register-submit" tabindex="4" class="form-control btn btn-register" value="Registrarse Ahora">-->
                                                    <input type="button" name="register-submit" id="register-submit" tabindex="4" class="form-control btn btn-register" value="Registrarse">
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                    
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="resources/html/footer.html" />
        <script src="resources/js/login.js" ></script>
    </body>

</html>
