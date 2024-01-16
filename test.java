import java.io.*;
import java.util.*;
import navigator.*;

class test {
    // la variable nav représente le dialogue avec le navigateur
    Navigator nav = new Navigator();
    int nbLigne = 10;
    int nbColonne = 10;
    String[][] tableur = new String[nbLigne+1][nbColonne+1];
    String[][] style = new String[nbLigne][nbColonne];
    String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    
    void inittab(){
        for(int i = 0; i < nbLigne; i++){
            for(int j = 0; j < nbColonne; j++){
                tableur[i][j]= "";
            }
        }
        for(int i = 0; i < nbLigne; i++){
            tableur[i+1][0]=Integer.toString(i+1);
        }
        for(int i = 0; i < nbColonne; i++){
            int div = i/26;
            if(div>0){
                for(int j=0;j<div;j++){
                    tableur[0][i+1]=alphabet[div-1]+alphabet[i%26];
                }
            }
            else tableur[0][i+1]=alphabet[i%26];
        }
    }

    void run() {
        inittab();
        while(true){
            nav.allowDownload("style.css");
            nav.beginPage();
            
            nav.println("""
            <html>
                <head>
                    <link href="style.css" rel="stylesheet" />
                </head>
                <body>
                    <div>
                        <form method="POST">
                            <fieldset>
                                <legend>Formulaire</legend>
                        
                                <label for="colonne">Case :</label> 
                                <input type="text" name="cellule">   
                                <label for="contenu">Contenu :</label> 
                                <input type="text" name="contenu">
                        
                                <fieldset>
                                    <legend>Gras</legend>
                
                                    <div>
                                        <input type="radio" name="gras" value="true" />
                                        <label for="ONg">ON ✅</label>
                                    </div>
                
                                    <div>
                                        <input type="radio" name="gras" value="false"/>
                                        <label for="OFFg">OFF ❌</label>
                                    </div>
                                </fieldset>
                
                                <fieldset>
                                    <legend>Italique</legend>
                                    <div>
                                        <input type="radio" name="italique" value="true" />
                                        <label for="ONi">ON ✅</label>
                                    </div>
                
                                    <div>
                                        <input type="radio" name="italique" value="false"/>
                                        <label for="OFFg">OFF ❌</label>
                                    </div>
                                </fieldset>

                                <fieldset>
                                    <legend>Couleur du texte</legend>
                                        <div>
                                            <input type="color" name="color" value="#e66465" />
                                        </div>
                                </fieldset>
                
                                <fieldset>
                                    <legend>Couleur d'arrière plan</legend>
                                        <div>
                                            <input type="color" name="background" value="#e66465" />
                                        </div>
                                </fieldset>
                
                                <input type="submit" name="valider" value="Valider">
                            </fieldset>
                        </form>
                    </div>
                    <div>
                        <table>
                            <tbody>""");
            
            int ligne = 0;
            int colonne = 0;
            if(nav.containsKey("cellule")){

                String cellule = nav.get("cellule");
                String[] mots = cellule.split(" ");
                
                String colonnestr = mots[0];
                String lignestr = mots[1];
                ligne = Integer.parseInt(lignestr);
                for(int i=0;i<nbColonne;i++){
                    if(tableur[0][i].equals(colonnestr)){
                        colonne=i;
                        }
                    }
            }

            if(nav.containsKey("contenu")){
                String contenu = nav.get("contenu");
                tableur[ligne][colonne] = contenu;
                
            }

            affiche_tab();

            nav.println("""
                            </tbody>
                        </table>
                    </div>""");


            nav.println("""
                </body>
            </html>
            """);
            nav.endPage();
        }
    }

    void affiche_tab(){

        for(int i = 0; i < nbLigne; i++){
            nav.print("\t\t</tr>");
            for(int j = 0; j < nbColonne; j++){
                nav.print("\t\t<td>");
                String cellule = tableur[i][j];
                nav.print(cellule);
                nav.print("</td>");
            }
            nav.println("\t\t</tr>");
        }

    }

    public static void main(String[] args) {
        new test().run();
    }
}

// <td class="" style="color:;">
// variable = "italique gras"

// for(int i = 0; i < nbLigne; i++){
//     nav.print("\t\t</tr>");
//     for(int j = 0; j < nbColonne; j++){
//         nav.print("\t\t<td class=\""+style[i][j]+"\">");
//         String cellule = tableur[i][j];
//         nav.print(cellule);
//         nav.print("</td>");
//     }
//     nav.println("\t\t</tr>");
// }
