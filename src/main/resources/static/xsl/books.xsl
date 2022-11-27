<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.0">
    <xsl:output method="html" indent="yes" />
    <xsl:template match="/">
        <html>
            <head>
                <title class="">Books</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
                      integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous"/>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
                        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
            </head>
            <body>
                <xsl:apply-templates />
            </body>
        </html>
    </xsl:template>
    <!--List for Filter and ArrayList for controller-->
    <xsl:template match="List|ArrayList">
        <div class="bg-image h-100" style="background-image: url('https://r4.wallpaperflare.com/wallpaper/548/555/607/old-books-wallpaper-a0593e636b613d8a40b0772e0834ab7d.jpg');">
            <div class="mask d-flex align-items-center h-100" style="background-color: rgba(0,0,0,.25);">
                <div class="container">
                    <div class="row justify-content-center">
                        <div class="col-12">
                            <div class="card bg-dark shadow-2-strong">
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table table-dark table-borderless mb-0">
                                            <thead>
                                                <tr>
                                                    <th scope="col">Id</th>
                                                    <th scope="col">Name</th>
                                                    <th scope="col">Author name</th>
                                                    <th scope="col">Genre</th>
                                                    <th scope="col">Price</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <xsl:for-each select="item">
                                                    <tr>
                                                        <td>
                                                            <xsl:value-of select="id" />
                                                        </td>
                                                        <td>
                                                            <xsl:value-of select="title" />
                                                        </td>
                                                        <td>
                                                            <xsl:value-of select="authorName" />
                                                        </td>
                                                        <td>
                                                            <xsl:value-of select="genre" />
                                                        </td>
                                                        <td>
                                                            <xsl:value-of select="price" />
                                                        </td>
                                                    </tr>
                                                </xsl:for-each>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </xsl:template>
</xsl:stylesheet>