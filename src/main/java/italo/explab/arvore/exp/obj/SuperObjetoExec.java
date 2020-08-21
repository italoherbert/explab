package italo.explab.arvore.exp.obj;

import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.atrib.Atrib;
import italo.explab.arvore.exp.variavel.VariavelExp;
import italo.explab.recursos.ClasseManager;
import italo.explab.recursos.classe.Classe;
import italo.explab.var.ObjetoVar;

public class SuperObjetoExec implements Exec {

    @Override
    public ExecResult exec(ExecNo no, Executor executor) {        
        SuperObjeto superObj = (SuperObjeto)no;
                
        ClasseManager classeManager = executor.getAplic().getClasseManager();
        
        String classeNome = superObj.getClasseNome();
        Classe classe = classeManager.buscaClasse( classeNome );
        ObjetoVar obj = classeManager.novoObjeto( classe );

        Atrib[] atribuicoes = new Atrib[ classe.getAtribuicoes().length ];
        for( int k = 0; k < atribuicoes.length; k++ ) {              
            Atrib atrib = (Atrib)classe.getAtribuicoes()[ k ].novo( superObj ); 

            ((VariavelExp)atrib.getVarExp()).runtimeConfigObjBusca( obj.getObjeto(), executor.getBuscaManager() ); 

            ExecResult er = executor.exec( atrib );            
            if ( er.isErroOuEx() )
                return er;
        }  

        return new ExecResult( obj );
    }
    
}
