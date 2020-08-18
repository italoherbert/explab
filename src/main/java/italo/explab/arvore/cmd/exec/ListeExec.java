package italo.explab.arvore.cmd.exec;

import italo.explab.ErroMSGs;
import italo.explab.InfoMSGs;
import italo.explab.PalavrasReservadas;
import italo.explab.arvore.BlocoNo;
import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.cmd.node.Liste;
import italo.explab.codigo.Codigo;
import italo.explab.construtor.Construtor;
import italo.explab.func.Func;
import italo.explab.inter.instrucao.CMDsInter;
import italo.explab.msg.CodigoErro;
import italo.explab.msg.MSGManager;
import italo.explab.recursos.RecursoManager;
import italo.explab.recursos.classe.Classe;
import italo.explab.recursos.classe.ClasseLista;
import italo.explab.recursos.construtor.ConstrutorLista;
import italo.explab.recursos.func.FuncLista;
import italo.explab.recursos.var.VarLista;
import italo.explab.recursos.var.Variavel;
import italo.explab.var.StringVar;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListeExec implements Exec {

    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {
        Liste liste = (Liste)no;
        
        String tipo = null;
        Classe classe = null;
        String termoBusca = null;
        
        Codigo codigo = no.getCodigo();
        
        BlocoNo bno = no.blocoNo();
        if ( bno == null ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, no.getI(), ErroMSGs.ERRO_RAIZ_NO_NAO_BLOCO_CODIGO );
            return new ExecResult( erro );
        }
        
        if ( liste.getTipoExp() != null ) {
            ExecResult er = executor.exec( liste.getTipoExp() );
            if ( er.isErroOuEx() )
                return er;
        
            tipo = ((StringVar)er.getVar()).getValor();
        }
        
        if ( liste.getClasseNomeExp() != null ) {
            ExecResult er = executor.exec( liste.getClasseNomeExp() );
            if ( er.isErroOuEx() )
                return er;            
        
            String classeNome = ((StringVar)er.getVar()).getValor();
                                                
            classe = executor.getBuscaManager().getClasseBusca().ignorePacoteBusca( bno, classeNome );                    
            if ( classe == null ) {                        
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, liste.getClasseNomeExp().getI(), ErroMSGs.CLASSE_NAO_ENCONTRADA, classeNome ); 
                return new ExecResult( erro );
            }                        
        }
             
        if ( liste.getTermoExp() != null ) {
            ExecResult er = executor.exec( liste.getTermoExp() );
            if ( er.isErroOuEx() )
                return er;                        
                        
            termoBusca = ((StringVar)er.getVar()).getValor();
        }
        
        MSGManager msgManager = executor.getAplic().getMSGManager();
        CMDsInter cmdsInter = executor.getAplic().getInterManager().getCMDsInter();
        RecursoManager recursos = executor.getAplic().getGlobalRecursoManager();
        
        if ( tipo != null ) {
            if ( classe == null ) {                
                if ( tipo.equalsIgnoreCase( PalavrasReservadas.FUNC ) ) {
                    FuncLista funcLista = recursos.getFuncLista();
                    this.listaFuncs( msgManager, funcLista, termoBusca );                                                
                } else if ( tipo.equalsIgnoreCase( PalavrasReservadas.VAR ) ) {
                    VarLista varLista = recursos.getVarLista();
                    this.listaVariaveis( msgManager, varLista, termoBusca );                     
                } else if ( tipo.equalsIgnoreCase( PalavrasReservadas.CLASSE ) ) {
                    ClasseLista classeLista = recursos.getClasseLista();
                    this.listaClasses( msgManager, classeLista, termoBusca );
                } else {
                    this.listaCMDs( msgManager, cmdsInter, termoBusca);
                }
            } else {
                if ( tipo.equalsIgnoreCase( PalavrasReservadas.FUNC ) ) {
                    FuncLista funcLista = classe.getClasseRecursoManager().getFuncLista();                                            
                    this.listaFuncs( msgManager, funcLista, termoBusca );
                } else if ( tipo.equalsIgnoreCase( PalavrasReservadas.CONSTRUTOR ) ) {
                    ConstrutorLista construtorLista = classe.getClasseRecursoManager().getConstrutorLista();                    
                    this.listaConstrutores( msgManager, construtorLista );
                } else {                    
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, liste.getTipoExp().getI(), ErroMSGs.CMD_TIPO_LISTAGEM_INVALIDO ); 
                    return new ExecResult( erro );
                }
            }
        }        

        return new ExecResult();                           
    }
    
    private void listaVariaveis( MSGManager msgManager, VarLista lista, String termoBusca ) {
        String listaDeVariaveisTexto = msgManager.getInfoMSG( InfoMSGs.LISTA_DE_VARIAVEIS );
        msgManager.envia( listaDeVariaveisTexto+"=" ); 
        List<Variavel> lst = new ArrayList( lista.getVariaveis());
        Collections.sort( lst, (Variavel v1, Variavel v2) -> {
            return ( v1.getNome().compareTo( v2.getNome() ) );
        } );
        for( Variavel v : lst ) {
            if ( termoBusca != null )
                if ( !v.getNome().toLowerCase().startsWith( termoBusca.toLowerCase() ) )
                    continue;
            
            msgManager.envia( "\n\t"+v.getNome() );
        }
    }
            
    private void listaFuncs( MSGManager msgManager, FuncLista lista, String termoBusca ) {
        String listaDeFuncoesTexto = msgManager.getInfoMSG( InfoMSGs.LISTA_DE_FUNCOES );
        msgManager.envia( listaDeFuncoesTexto+"=" ); 
        List<Func> lst = new ArrayList( lista.getFuncs() );
        Collections.sort( lst, (Func f1, Func f2) -> {
            return ( f1.getNome().compareTo( f2.getNome() ) );
        } );
        for( Func f : lst ) {
            if ( termoBusca != null )
                if ( !f.getNome().toLowerCase().startsWith( termoBusca.toLowerCase() ) )
                    continue;            
            
            msgManager.envia( "\n\t" + f.getNome() + "  " );
            msgManager.envia( String.valueOf( f.getQuantParametros() ) ); 
        }
    }
    
    private void listaConstrutores( MSGManager msgManager, ConstrutorLista lista ) {
        String listaDeConstrutoresTexto = msgManager.getInfoMSG( InfoMSGs.LISTA_DE_CONSTRUTORES );                
        msgManager.envia( listaDeConstrutoresTexto+"=" ); 
        int i = 1;
        for( Construtor c : lista.getConstrutores() ) {
            String istr = String.valueOf( i );
            String nparsstr = String.valueOf( c.getParametros().length );
            String desc = msgManager.getInfoMSG( InfoMSGs.LISTA_DE_CONSTRUTORES_DESC, istr, nparsstr );
            msgManager.envia( "\n\t"+desc );        
            i++;
        }
    }
    
     private void listaCMDs( MSGManager msgManager, CMDsInter cmdsInter, String termoBusca ) {
        String listaDeFuncoesTexto = msgManager.getInfoMSG( InfoMSGs.LISTA_DE_CMDS );
        msgManager.envia( listaDeFuncoesTexto+"=" ); 
        List<String> lst = new ArrayList( cmdsInter.getInstrucoesCMDsMap().keySet() );
        Collections.sort( lst, (String s1, String s2) -> {
            return ( s1.compareTo( s2 ) );
        } );
        for( String cmd : lst ) {
            if ( termoBusca != null )
                if ( !cmd.toLowerCase().startsWith( termoBusca.toLowerCase() ) )
                    continue;            
            
            msgManager.envia( "\n\t" + cmd );
        }
    }
    
    private void listaClasses( MSGManager msgManager, ClasseLista lista, String termoBusca ) {
        String listaDeFuncoesTexto = msgManager.getInfoMSG( InfoMSGs.LISTA_DE_CLASSES );
        msgManager.envia( listaDeFuncoesTexto+"=" ); 
        List<Classe> lst = new ArrayList( lista.getClasses() );
        Collections.sort( lst, (Classe c1, Classe c2) -> {
            return ( c1.getNome().compareTo( c2.getNome() ) );
        } );
        for( Classe c : lst ) {
            if ( termoBusca != null )
                if ( !c.getNome().toLowerCase().startsWith( termoBusca.toLowerCase() ) )
                    continue;            
            
            msgManager.envia( "\n\t" + c.getNome() );
        }
    }
    
}
