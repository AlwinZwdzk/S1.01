/*Alwin Zawadzki - Nicolas Delhaye - Antoine Hugot*/

import java.io.*;
import java.util.*;
import navigator.*;

class tableur {
    Navigator nav = new Navigator();
    int nbLigne = 10;
    int nbColonne = 10;
    String[][] tableur = new String[nbLigne+1][nbColonne+1];
    String[][] style = new String[nbLigne][nbColonne];
    String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    String style_ajout = "";

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
                                <input placeholder="Exemple : B5" type="text" name="cellule">   
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

                                <fieldset>
                                    <legend>Encadrement de la cellule</legend>
                                        <div>
                                            <select name="encadrement">
                                                <option value="encadrement1">1px</option>
                                                <option value="encadrement2">2px</option>
                                                <option value="encadrement3">3px</option>
                                                <option value="encadrement5">5px</option>
                                                <option value="encadrement7">7px</option>
                                                <option value="encadrement9">9px</option>
                                            </select>

                                            <select name="encadrement_type">
                                                <option value="plein">Plein</option>
                                                <option value="pointilles">Pointillés</option>
                                                <option value="tirets">Tirets</option>
                                            </select>
                                        </div>
                                </fieldset>
                
                                <input type="submit" name="valider" value="Valider">
                            </fieldset>
                        </form>
                    </div>
                    <div>
                        <!-- Formulaire de recherche et remplacement -->
                            <form method="POST">
                                <fieldset>
                                        <legend>Rechercher un mot et le remplacer</legend>

                                        <label for="recherche">Mot à rechercher :</label>
                                        <input type="text" name="recherche">

                                        <label for="remplacement">Mot de remplacement :</label>
                                        <input type="text" name="remplacement">

                                        <input type="submit" name="rechercherRemplacer" value="Rechercher et Remplacer">
                                </fieldset>
                            </form>
                    </div>
                    <div>
                        <table>
            """);
            
            style_ajout = "";
            gras();
            italique();
            color();
            background();
            encadrement();
            encadrement_type();

            if (nav.containsKey("rechercherRemplacer") && nav.containsKey("recherche") && nav.containsKey("remplacement")) {
                String recherche = nav.get("recherche");
                String remplacement = nav.get("remplacement");
                rechercherRemplacer(recherche,remplacement);
            }
            if(nav.containsKey("cellule") && nav.containsKey("contenu")){
                String contenu = nav.get("contenu");
                String cellule = nav.get("cellule");
                modif_cellule(contenu,cellule);
            }
            affiche_tab();
            
            nav.println("""
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
                nav.print("\t\t<td class=\""+style[i][j]+"\">");
                String cellule = tableur[i][j];
                nav.print(cellule);
                nav.print("</td>");
            }
            nav.println("\t\t</tr>");
        }

    }


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


    void modif_cellule(String contenu, String cellule){
                if(contenu.charAt(0)=='='){
                    String[] op = contenu.split(" ");
                    double res = calcul(Double.parseDouble(op[1]),Double.parseDouble(op[2]),op[3].charAt(0));
                    int len_op = op.length;
                    int j=5;
                    while(j<=len_op){
                        res = calcul(res,Double.parseDouble(op[j-1]),op[j].charAt(0));
                        j+=2;
                    }
                    contenu = Double.toString(res);
                } 

                int ligne=0;
                int colonne=0;
                String[] coord = coordonnees(cellule);
                ligne = Integer.parseInt(coord[0]);
                for(int j=0;j<nbColonne;j++){
                    if(tableur[0][j].equals(coord[1])){
                        colonne=j;
                    }
                }
                tableur[ligne][colonne] = contenu; 
                style[ligne][colonne] = style_ajout;
    }

    void gras(){
        if(nav.containsKey("gras")){
            String gras = nav.get("gras");
            if(gras.equals("true")){
                style_ajout = style_ajout + "gras ";
            }
        }
    }
    
    void italique(){
        if(nav.containsKey("italique")){
            String italique = nav.get("italique");
            if(italique.equals("true")){
                style_ajout = style_ajout + "italique ";
            }
        }
    }

    void color(){
        if(nav.containsKey("color")){
            String color = nav.get("color");
            style_ajout = style_ajout + color + " ";
        }
    }

    void background(){
        if(nav.containsKey("background-color")){
            String background = nav.get("background-color");
            style_ajout = style_ajout + background + " ";
        }
    }

    //Sépare les coordonnées de la ligne et la colone
    //Par exemple, coord("BA123") renvoie coord = {"BA","123"}
    String[] coordonnees(String cellule){
        int i=0;
        String[] coord = {"",""};
        while(i<cellule.length() && inAlphabet(cellule.charAt(i))){
            coord[1]+=cellule.charAt(i);
            i++;
        }
        while(i<cellule.length()){
            coord[0]+=cellule.charAt(i);
            i++;
        }
        return coord;
    }

    void style(String caracteristique){
        if(nav.containsKey(caracteristique)){
            String gras = nav.get(caracteristique);
            if(gras.equals("true")){
                style_ajout = style_ajout + caracteristique+" ";
            }
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

    double calcul(double x1,double x2, char op){
        switch(op){
            case '+':
                return x1+x2;
            case '-':
                return x1-x2;
            case '*':   
                return x1*x2;
            case '/':
                if(x2!=0) return x1/x2;
                return -1;
        } 
        return -1;
    }

    /*Nicolas Delhaye - Fonctionnalité additionnelle n°8 : encadrement des cellules*/
    void encadrement(){
        if(nav.containsKey("encadrement")){
            String encadrement = nav.get("encadrement");
            style_ajout = style_ajout + encadrement + " ";
        }
    }

    void encadrement_type(){
        if(nav.containsKey("encadrement_type")){
            String encadrement_type = nav.get("encadrement_type");
            style_ajout = style_ajout + encadrement_type + " ";
        }
    }

    /*Antoine Hugot -  - Fonctionnalité additionnelle n°14 : rechercher et remplacer un mot*/
    void rechercherRemplacer(String recherche,String remplacement){            
        for (int i = 0; i < nbLigne; i++) {
            for (int j = 0; j < nbColonne; j++) {
                if (tableur[i][j].contains(recherche)) {
                    tableur[i][j] = tableur[i][j].replace(recherche, remplacement);
                }
            }
        }
    }
    
    public static void main(String[] args) {
        new tableur().run();
    }
}
