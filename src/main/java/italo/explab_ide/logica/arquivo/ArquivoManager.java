package italo.explab_ide.logica.arquivo;

import italo.explab_ide.logica.arquivo.projeto.Projeto;
import italo.explab_ide.ExpLabIDEAplic;
import java.io.File;
import libs.gui.arv.ArvNo;

public class ArquivoManager {
    
    private final ExpLabIDEAplic aplic;
    
    public ArquivoManager( ExpLabIDEAplic aplic ) {
        this.aplic = aplic;
    }
            
    public void move( String orig, String dest ) {                       
        File origFile = new File( orig );
        String arq = origFile.getName();         
        
        String novo = dest;
        if ( !novo.endsWith( "/" ) )
            novo += "/";
        novo += arq;
        
        File novoFile = new File( novo ); 
        origFile.renameTo( novoFile );        
    }

    public void delete( String caminho ) {
        this.delete( new File( caminho ) );
    }
    
    public void delete( File file ) {
        if ( file.isDirectory() ) {
            File[] files = file.listFiles();
            for( File f : files )
                this.delete( f );            
        }        
        file.delete();
    }
    
    public ArqArvNo geraArvRaiz( Projeto proj ) {
        File file = new File( proj.getBasedir() );
        if ( file.exists() ) {
            ArqArvNo raiz = new ArqArvNo();
            raiz.setEhPastaDeProjeto( true ); 
            raiz.setNome( proj.getNome() );
            raiz.setSisArqCaminho( file.getAbsolutePath() );
            raiz.setEhPasta( true );
            raiz.setProjeto( proj );            
            if ( file.isDirectory() ) {
                File[] lista = file.listFiles();
                this.carregaFilhos( proj, raiz, lista );
                
                raiz.setEhVazio( lista.length == 0 );
            }
            return raiz;
        }
        return null;
    }
    
    public void carregaSubArv( ArqArvNo no ) {
        File file = new File( no.getSisArqCaminho() );
        if ( file.isDirectory() ) {
            no.setFilhos( null ); 
            
            File[] lista = file.listFiles();
            this.carregaFilhos( no.getProjeto(), no, lista );            

            no.setEhVazio( lista.length == 0 );
        }
    }
        
    public void carregaArv( Projeto proj, ArvNo parente, File file ) {
        ArqArvNo no = new ArqArvNo();
        no.setEhPastaDeProjeto( false ); 
        no.setNome( file.getName() ); 
        no.setSisArqCaminho( file.getAbsolutePath() );
        no.setEhPasta( file.isDirectory() );
        no.setEhVazio( true );
        no.setProjeto( proj ); 
        if ( file.isDirectory() ) {            
            File[] lista = file.listFiles();
            this.carregaFilhos( proj, no, lista );
            
            no.setEhVazio( lista.length == 0 );
        }
        
        if ( parente != null )
            parente.addNoFilho( no );   
    }
    
    private void carregaFilhos( Projeto proj, ArqArvNo no, File[] files ) {
        String elext = aplic.getConfig().getScriptELExt();
        String projConfigArqPadrao = aplic.getConfig().getProjetoConfigArqPadrao();
        if ( files != null ) {
            for( File f : files )
                if ( f.isDirectory() )
                    this.carregaArv( proj, no, f );
            for( File f : files )
                if ( !f.isDirectory() )
                    if ( f.getName().endsWith( elext ) )
                        this.carregaArv( proj, no, f );
            for( File f : files )
                if ( !f.isDirectory() )
                    if ( !f.getName().equalsIgnoreCase( projConfigArqPadrao ) && !f.getName().endsWith( elext ) )
                        this.carregaArv( proj, no, f );
            for( File f : files )
                if ( !f.isDirectory() )
                    if ( f.getName().equalsIgnoreCase( projConfigArqPadrao ) )
                        this.carregaArv( proj, no, f );
        }
    }

    
}
