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
