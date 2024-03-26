import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class APP {
    Character[][] matriz;
    int inicioX = 0;
    int inicioY = 0;
    int direcaoX;
    int direcaoY;
    int soma = 0;

    public void carregarMapa(String nome) {
        soma = 0;
        Path path = Paths.get(nome + ".txt"); //Indica o caminho até o arquivo .txt
        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.ISO_8859_1)) {
            String linha = br.readLine();
            String aux = "";
            int tamanhoX = 0;
            int tamanhoY = 0;
            int pos = 0;
            for (int i = 0; i < linha.length(); i++){
                if(linha.charAt(i) == ' '){ //Se o charAt(i) retornar vazio, significa que todos os números que indicam a altura da matriz foram lidos
                    tamanhoY = Integer.parseInt(aux);
                    pos = i + 1; //Indica a posição onde o valor que indica a largura da matriz se encontra
                    i = linha.length(); //Interrompe o for
                } else{
                    aux += linha.charAt(i);
                }
            }
            aux = ""; //Limpa a variável aux

            for(int i = pos; i < linha.length(); i++){
                aux += linha.charAt(i); //Lê e armazena todos o valor que indica a largura da matriz
            }
            tamanhoX = Integer.parseInt(aux);

            matriz = new Character[tamanhoY][tamanhoX]; //Cria a matriz com o valor de Altura e Largura lidos anteriormente

            for(int i = 0; i < tamanhoX; i++){
                linha = br.readLine();
                for (int j = 0; j < tamanhoY; j++) {
                    try {
                        matriz[i][j] = linha.charAt(j); //Preenche a matriz com todos os valores do .txt
                    } catch(StringIndexOutOfBoundsException ex){
                        matriz[i][j] = 'n';
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Arquivo não encontrado.");
            System.exit(0);
        }
    }

    public void imprimirElementos(){
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void encontrarInicio(){ //Percorre toda a primeira coluna até encontrar o caractere '-' que indica o início do percurso
        for (int i = 0; i < matriz.length; i++) {
            if(matriz[i][0] == '-') {
                inicioY = i;
                return;
            }
        }
    }

    public void lerElementos(){
        encontrarInicio();
        char lido = ' ';
        direcaoY = 0;
        //direcaoY = 1 indica que o ponteiro deve percorrer pra cima, direcaoY = -1 indica que deve percorrer pra baixo
        direcaoX = 1;
        //direcaoX = 1 indica que o ponteiro deve percorrer pra direita, direcaoX = -1 indica que deve percorrer pra esquerda
        //Quando X ou Y forem 0, indica que o ponteiro não deve se mover naquela direção
        int posX = inicioX;
        int posY = inicioY;
        while(lido != '#'){ //# indica o fim do mapa
            lido = matriz[posY][posX];
            switch(lido){
                case '-' ->{
                    if(direcaoX == 1){
                        posX++;
                    } else if(direcaoX == -1){
                        posX--;
                    } else if(direcaoY == 1)
                    {
                        posY--;
                    } else if(direcaoY == -1){
                        posY++;
                    }
                }
                case '/' ->{
                    if(direcaoX == 1){
                        direcaoX = 0;
                        direcaoY = 1;
                        posY--;
                    } else if(direcaoY == 1){
                        direcaoY = 0;
                        direcaoX = 1;
                        posX++;
                    } else if(direcaoX == -1){
                        direcaoX = 0;
                        direcaoY = -1;
                        posY++;
                    } else if(direcaoY == -1){
                        direcaoY = 0;
                        direcaoX = -1;
                        posX--;
                    }
                }
                case '|' ->{
                    if(direcaoY == 1){
                        posY--;
                    } else if(direcaoY == -1){
                        posY++;
                    } else{
                        if(direcaoX == 1) posX++;
                        else if(direcaoX == -1) posX--;
                    }
                }
                case '\\' ->{
                    if(direcaoX == 1){
                        direcaoX = 0;
                        direcaoY = -1;
                        posY++;
                    } else if(direcaoY == 1){
                        direcaoY = 0;
                        direcaoX = -1;
                        posX--;
                    } else if(direcaoX == -1){
                        direcaoX = 0;
                        direcaoY = +1;
                        posY--;
                    } else if(direcaoY == -1){
                        direcaoY = 0;
                        direcaoX = +1;
                        posX++;
                    }
                }
                default ->{
                    if(Character.isDigit(lido)){
                        String number = "";
                        number += lido;
                        if(direcaoX == 1){
                            while(Character.isDigit(peek(posX+1, posY))){
                                number += peek(posX+1, posY);
                                posX++;
                            }
                            posX++;
                            lido = matriz[posY][posX];
                        } else if(direcaoX == -1){
                            while(Character.isDigit(peek(posX-1, posY))){
                                number += peek(posX-1, posY);
                                posX--;
                            }
                            posX--;
                            lido = matriz[posY][posX];
                        } else if(direcaoY == 1){
                            while(Character.isDigit(peek(posX, posY-1))){
                                number += peek(posX, posY-1);
                                posY--;
                            }
                            posY--;
                            lido = matriz[posY][posX];
                        } else if(direcaoY == -1){
                            while(Character.isDigit(peek(posX, posY+1))){
                                number += peek(posX, posY+1);
                                posY++;
                            }
                            posY++;
                            lido = matriz[posY][posX];
                        }
                        soma = soma + Integer.parseInt(number);
                    }
                }
            }
        }
    }
    public char peek(int posX, int posY){
        if (posY + 1 >= matriz.length || posX > matriz[posY].length) return 'o';
        if(Character.isDigit(matriz[posY][posX])) return matriz[posY][posX];
        return 'o';
    }
    public int getSoma(){
        return soma;
    }
}
