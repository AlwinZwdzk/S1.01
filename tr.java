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
    
