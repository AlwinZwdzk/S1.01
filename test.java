import java.io.*;
import java.util.*;
import navigator.*;

class test {
    // la variable nav représente le dialogue avec le navigateur
    Navigator nav = new Navigator();
    //nav.allowDownload("style.css");
    int nbLigne = 9;
    int nbColonne = 9;
    
    void run() {
        nav.beginPage();
        nav.println("""
<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="UTF-8" />
        <title>Document</title>
    </head>
    <body>
    <table>""");
        for(int i = 0; i < nbLigne; i++){
            nav.println("""
        <tr>""");
            for(int j = 0; j < nbColonne; j++){
                nav.println("""
            <td>""");
                String xText = Integer.toString(j);
                nav.print(xText);
                nav.print("</td>");
            }
            nav.println("""
        </tr>""");
        }
        nav.println("""
    </table>
    </body>
    </html>""");
        nav.endPage();
    }

    public static void main(String[] args) {
        new test().run();
    }
}
