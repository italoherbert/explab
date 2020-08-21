package italo.explab.arvore.exp.obj;

import italo.explab.ErroMSGs;
import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.atrib.Atrib;
import italo.explab.arvore.classe.ClasseConstrutor;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.exp.ExpRecurso;
import italo.explab.arvore.exp.variavel.VariavelExp;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.recursos.ClasseManager;
import italo.explab.recursos.classe.Classe;
import italo.explab.var.ObjetoVar;

public class NovoObjetoExec implements Exec {

    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {
        NovoObjeto novoObj = (NovoObjeto)no;                
        
        Codigo codigo = no.getCodigo();
        
        ClasseManager classeManager = executor.getAplic().getClasseManager();        
        
        Classe classe = classeManager.buscaClasse( novoObj.getClasseNome() );                       
        if ( classe == null ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, novoObj.getI(), ErroMSGs.CLASSE_NAO_ENCONTRADA, novoObj.getClasseNome() );
            return new ExecResult( erro );
        }  
        
        ObjetoVar objvar = classeManager.novoObjeto( classe );        
        objvar.getObjeto().setContainerObjeto( novoObj.objeto( executor ) ); 

        novoObj.setRuntimeObjeto( objvar.getObjeto() ); 

        ObjetoVar obj = objvar;
             
        Classe c = classe;
        if ( c.getSuperClasse() != null ) {
            SuperObjeto superObj = executor.getExecManager().getExecNoFactory().getExpFactory().novoSuperObjeto( no.getI(), no, codigo );
            superObj.setClasseNome( c.getSuperClasse().getNomeCompleto() );
            novoObj.setSuperObjeto( superObj );
                                    
            c = c.getSuperClasse().getSuperClasse();
            
            while ( c != null ) {  
                SuperObjeto superObjeto = executor.getExecManager().getExecNoFactory().getExpFactory().novoSuperObjeto( no.getI(), no, codigo );
                superObjeto.setClasseNome( c.getNomeCompleto() ); 
                
                superObj.setParente( superObjeto );                
                superObj.setSuperObjeto( superObjeto );                 
                superObj = superObjeto;

                c = c.getSuperClasse();
            }
        }     
                        
        SuperObjeto superObj = novoObj.getSuperObjeto();
        while ( superObj != null ) {            
            ExecResult er = executor.exec( superObj );
            if ( er.isErroOuEx() )
                return er;
            
            ObjetoVar o = (ObjetoVar)er.getVar();
            
            obj.getObjeto().setSuperObjeto( o.getObjeto() );                                    
            obj = o;            
            
            superObj = superObj.getSuperObjeto();
        }
                                   
        ClasseConstrutor construtor = executor.getExecManager().getExecNoFactory().novoConstrutor( novoObj.getI(), novoObj, codigo );
        construtor.setParams( novoObj.getConstrutorParams() );
                         
        Atrib[] atribuicoes = new Atrib[ classe.getAtribuicoes().length ];
        for( int k = 0; k < atribuicoes.length; k++ ) {              
            Atrib atrib = (Atrib)classe.getAtribuicoes()[ k ].novo( novoObj ); 
            
            ((VariavelExp)atrib.getVarExp()).runtimeConfigObjBusca( objvar.getObjeto(), executor.getBuscaManager() ); 
                                                
            ExecResult er = executor.exec( atrib );            
            if ( er.isErroOuEx() )
                return er;
        }                
                                   
        ExecResult result = executor.exec( construtor );
        if ( result.isErroOuEx() )
            return result;
                        
        if ( novoObj.getOOChamador() != null ) {                                             
            ExpRecurso chamada = novoObj.getOOChamador().getChamada();
            chamada.runtimeConfigObjBusca( objvar.getObjeto(), executor.getBuscaManager() ); 
            
            ExecResult er = executor.exec( (Exp)chamada );
            if ( er.isErroOuEx() )
                return er;                

            return new ExecResult( er.getVar() );
        }
                                
        return new ExecResult( objvar );
    }
    
}
