package principal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class HandlerFicheiro {
	
	
	
	public static void escreveEmFx(String input) {    	
    	byte [] byteArray = (input + "\n").getBytes();
    	ByteBuffer buffer = ByteBuffer.wrap(byteArray);
    	
    	Path path = Paths.get("./fxAux/output.LOG");    	
    		
    	AsynchronousFileChannel channel = null;
    	FileLock lock = null;
    	try {
    		
			channel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
			
			Future<FileLock> featureLock = channel.lock();
			lock = featureLock.get();
			if (lock.isValid()) {
				Future<Integer> featureWrite = channel.write(buffer, channel.size());
			}
			lock.release();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
    	finally { 
    		if (lock != null && lock.isValid()) { try { lock.release(); } catch (IOException e) { e.printStackTrace(); } }
    		if (channel != null && channel.isOpen()) { try { channel.close(); } catch (IOException e1) { e1.printStackTrace(); } }    		
    	}    	
    }
	
	public static StringBuffer abrirFicheiro(String pathFx) {
    	
		StringBuffer sudokuStringBuffer = null;
		
		File file = new File(pathFx);    	
    	
    	sudokuStringBuffer = new StringBuffer();
    	BufferedReader br = null;
    	try {
			br = new BufferedReader(new FileReader(file));
			String linha;
			
			while ((linha = br.readLine()) != null) {
				sudokuStringBuffer.append(linha + "\n");				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	finally {
    		if (br != null) { try { br.close(); } catch (IOException e) { /* TODO Auto-generated catch block*/ e.printStackTrace(); } }
    	}    
    	return sudokuStringBuffer;
    }
	
	public static List<String> getListaFicheiros() {
    	
		List<String> listaFicheiros = new ArrayList<>();		
		File file = new File("./fxAux/ficheiros.TXT");    
		String pathFx = "./puzzlesSudoku";
    	
    	BufferedReader br = null;
    	try {
			br = new BufferedReader(new FileReader(file));
			String linha;
			
			while ((linha = br.readLine()) != null) {
				listaFicheiros.add(pathFx + "/" + linha);			
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	finally {
    		if (br != null) { try { br.close(); } catch (IOException e) { /* TODO Auto-generated catch block*/ e.printStackTrace(); } }
    	}    
    	return listaFicheiros;
    }
	
	public static List<String> getPathsAbsolutasFicheiros() {
		ficheiros = new ArrayList<>();
		listFilesForFolder(pastaSudokus);
		return ficheiros;
	}
	
	private static void listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
            	ficheiros.add(fileEntry.getAbsolutePath());                
            }
        }
    }
	
	private static List<String> ficheiros;
	private static final File pastaSudokus = new File("./puzzlesSudoku");
}
