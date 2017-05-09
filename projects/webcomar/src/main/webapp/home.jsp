<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, shrink-to-fit=no, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Comar</title>

        <jsp:include page="resources/html/header.html" />
    </head>

    <body>

        <div id="wrapper" class="toggled">

            <!-- Sidebar -->
            <jsp:include page="resources/html/nav.html" />

            <!-- Page Content -->
            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-12">
                            <h1>Simple Sidebar</h1>
                            <p>This template has a responsive menu toggling system. The menu will appear collapsed on smaller screens, and will appear non-collapsed on larger screens. When toggled using the button below, the menu will appear/disappear. On small screens, the page content will be pushed off canvas.</p>
                            <p>Make sure to keep all page content within the <code>#page-content-wrapper</code>.</p>
                            <a href="#menu-toggle" class="btn btn-default" id="menu-toggle">Toggle Menu</a>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /#page-content-wrapper -->

        </div>
        <!-- /#wrapper -->

        <jsp:include page="resources/html/footer.html" />
        <script src="resources/js/index.js" ></script>
    </body>

</html>
