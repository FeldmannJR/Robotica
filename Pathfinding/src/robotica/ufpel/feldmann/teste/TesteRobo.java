package robotica.ufpel.feldmann.teste;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Sound;
import robotica.ufpel.feldmann.Controller;
import robotica.ufpel.feldmann.Debug;
import robotica.ufpel.feldmann.Direction;
import robotica.ufpel.feldmann.teste.FNodo.NodoStatus;

public class TesteRobo {
	private static final int SIZE =4;

	private static FNodo[][] nodos = new FNodo[SIZE][SIZE];
	public static FNodo atual;
	
	public static void initNodos() {
		for(byte x=0;x<SIZE;x++) {
			for(byte y=0;y<SIZE;y++) {
				nodos[x][y]= new FNodo(x,y);
			}
		}
	}
	public static FNodo get(int x,int y) {
		if(x>=0 && x<SIZE&&y>=0&&y<SIZE) {
			return nodos[x][y];
		}
		return null;
	}
	public static FNodo geVizinho(FNodo nodo,Direction dir) {
		switch (dir) {
		case NORTH:
			return get(nodo.getX(),nodo.getY()+1);
		case SOUTH:
			return get(nodo.getX(),nodo.getY()-1);
		case EAST:
			return get(nodo.getX()+1,nodo.getY());
		case WEST:
			return get(nodo.getX()-1,nodo.getY());
		}
		return null;
	}
	
	public static void main(String[] args) {
		initNodos();
		Debug.alwaysExit();
		Random rnd = new Random();
		Controller pilot = new Controller();
		atual = get(SIZE-1, SIZE-1);
		atual.visitado = true;
		atual.status = NodoStatus.EMPTY;
		Button.waitForAnyPress();
		do {
			int x =0;
			FNodo[] vizinhos = getVizinhos(atual);
			FNodo go = null;
			for(FNodo vizinho : vizinhos) {
				if(vizinho.status==NodoStatus.UNKOWN) {
					vizinho.status = pilot.hasBarrier(atual, vizinho)?NodoStatus.BARRIER:NodoStatus.EMPTY;
					Sound.playTone(500+(50*x++), 1000);
					if(vizinho.status==NodoStatus.BARRIER) {
						Sound.playTone(200+(50*(x-1)), 1000);
					}
				}
				if(!vizinho.visitado) {
					if(go==null) {
						go = vizinho;
					}
				}
			}
			if(go!=null) {
				go.visitado = true;
				pilot.move(atual, go);
				atual = go;
			}else {
				drawGrid();
				Sound.playTone(150, 2000);
				break;
			}
			
			

			
			
			drawGrid();
			
		}while(true);
		Button.waitForAnyPress();
	}
	
	public static void drawGrid() {
		for(int x=0;x<SIZE;x++) {
			for(int y=0;y<SIZE;y++) {
				char c = '?';
				
				if(atual.x == x && atual.y==y) {
					c = 'E'; 
				}else
				if(get(x,y).status==NodoStatus.BARRIER) {
					c = 'X';
				}else if(get(x,y).status==NodoStatus.EMPTY) {
					c = 'O';		
				}
				LCD.drawChar(c, x, y);
			}
		}
		
	}
	
	public static FNodo[] getVizinhos(FNodo nodo){
		int tam = 4;
		if(nodo.getX() == 0 || nodo.getX()==(SIZE-1)) {
			tam--;
		}
		if(nodo.getY()==0 || nodo.getY()==(SIZE-1)) {
			tam--;
		}
		FNodo[] vizinhos = new FNodo[tam];
		int slot =0 ;
		for(int[] v : new int[][] {{0,1},{1,0},{0,-1},{-1,0}}) {
			int nx = nodo.getX()+v[0];
			int ny = nodo.getY()+v[1];
			if(nx >= 0 && nx<SIZE && ny>= 0 && ny<SIZE) {
				vizinhos[slot++]=get(nx, ny);
			}
		}
		return vizinhos;
		
		
	}

}
