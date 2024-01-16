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
    
    void init_tab_style(){
        for(int i = 0; i < nbLigne; i++){
            for(int j = 0; j < nbColonne; j++){
                tableur[i][j]= "";
                style[i][j]= "";
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
        init_tab_style();
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
                                <input placeholder="Exemple : B 5" type="text" name="cellule">   
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
                                    <legend>Couleur du Texte</legend>
                                        <div>
                                            <select name="color">
                                                <option value="black">Noir ⚫</option>
                                                <option value="white">Blanc ⚪</option>
                                                <option value="red">Rouge 🔴</option>
                                                <option value="brown">Marron 🟤</option>
                                                <option value="green">Vert 🟢</option>
                                                <option value="yellow">Jaune 🟡</option>
                                                <option value="blue">Bleu 🔵 </option>
                                                <option value="purple">Violet 🟣 </option>
                                            </select>
                                        </div>
                                </fieldset>

                                <fieldset>
                                    <legend>Couleur de Fond</legend>
                                        <div>
                                            <select name="background-color">
                                                <option value="bgwhite">Blanc ⚪</option>
                                                <option value="bgblack">Noir ⚫</option>
                                                <option value="bgred">Rouge 🔴</option>
                                                <option value="bgbrown">Marron 🟤</option>
                                                <option value="bgreen">Vert 🟢</option>
                                                <option value="bgyellow">Jaune 🟡</option>
                                                <option value="bgblue">Bleu 🔵 </option>
                                                <option value="bgpurple">Violet 🟣 </option>
                                            </select>
                                        </div>
                                </fieldset>
                
                                <input type="submit" name="valider" value="Valider">
                            </fieldset>
                        </form>
                    </div>
                    <div>
                        <table>
                            <tbody>""");
            
            modif_cellule();
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

        // for(int i = 0; i < nbLigne; i++){
        //     nav.print("\t\t</tr>");
        //     for(int j = 0; j < nbColonne; j++){
        //         nav.print("\t\t<td>");
        //         String cellule = tableur[i][j];
        //         nav.print(cellule);
        //         nav.print("</td>");
        //     }
        //     nav.println("\t\t</tr>");
        // }
        for(int i = 0; i < nbLigne; i++){
            nav.print("\t\t</tr>");
            for(int j = 0; j < nbColonne; j++){
                nav.print("\t\t<td class=\""+style[i][j]+"\">");
                String cellule = tableur[i][j];
                nav.print(cellule);
                nav.print("</td>");
            }
            nav.println("\t\t</tr>");
        }

    }

    void modif_cellule(){
            int ligne = 0;
            int colonne = 0;
            if(nav.containsKey("cellule") && nav.containsKey("contenu")){

                String cellule = nav.get("cellule");
                int i=0;
                String colonnestr = "";
                String lignestr = "";
                while(i<cellule.length() && inAlphabet(cellule.charAt(i))){
                    colonnestr+=cellule.charAt(i);
                    i++;
                }
                while(i<cellule.length()){
                    lignestr+=cellule.charAt(i);
                    i++;
                }
                
                
                ligne = Integer.parseInt(lignestr);
                for(int i=0;i<nbColonne;i++){
                    if(tableur[0][i].equals(colonnestr)){
                        colonne=i;
                    }
                }
                String contenu = nav.get("contenu");
                tableur[ligne][colonne] = contenu;

                String style_ajout = "";
                if(nav.containsKey("gras")){
                    String gras = nav.get("gras");
                    if(gras.equals("true")){
                        style_ajout = style_ajout + "gras ";
                    }
                }
                if(nav.containsKey("italique")){
                    String italique = nav.get("italique");
                    if(italique.equals("true")){
                        style_ajout = style_ajout + "italique ";
                    }
                }

                if(nav.containsKey("color")){
                    String color = nav.get("color");
                    style_ajout = style_ajout + color + " ";
                }

                if(nav.containsKey("background-color")){
                    String background = nav.get("background-color");
                    style_ajout = style_ajout + background + " ";
                }

                style[ligne][colonne] = style_ajout;
                System.out.println(style_ajout);
            }
    }

    boolean inAlphabet(char caractere){
        for(int i=0;i<alphabet.length;i++){
            if(caractere==alphabet[i].charAt(0)){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        new test().run();
    }
}

