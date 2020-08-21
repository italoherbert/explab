package italo.explab.arvore.exp.matriz;

import italo.explab.ErroMSGs;
import italo.explab.arvore.ExObj;
import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.OOChamada;
import italo.explab.arvore.exp.VarOuFuncExecResult;
import italo.explab.arvore.exp.matriz.indice.ExpMatIDs;
import italo.explab.arvore.exp.matriz.indice.ExpMatrizIndice;
import italo.explab.arvore.exp.variavel.VariavelExp;
import italo.explab.codigo.Codigo;
import italo.explab.matriz.MatrizELResult;
import italo.explab.matriz.MatrizManager;
import italo.explab.matriz.indice.ArrayVO;
import italo.explab.matriz.indice.MatrizIndice;
import italo.explab.msg.CodigoErro;
import italo.explab.msg.Erro;
import italo.explab.var.Var;
import italo.explab.var.mat.MatrizVar;
import italo.explab.var.num.RealVar;
import italo.explab.var_alterador.MatrizAlterador;

public class MatrizExpExec implements Exec {

    @Override
    public MatrizExecResult exec( ExecNo no, Executor executor ) {        
        MatrizExp matExp = (MatrizExp)no;
        OOChamada ooChamador = matExp.getOOChamador();        
                       
        Codigo codigo = no.getCodigo();
                                     
        VariavelExp varExp = executor.getExecManager().getExecNoFactory().getExpFactory().novoVariavelExp( no.getI(), no, codigo );
        varExp.setNome( matExp.getNome() );
        varExp.setAcesso( matExp.getAcesso() );
        varExp.setOOChamador( ooChamador );
               
        VarOuFuncExecResult er = (VarOuFuncExecResult)executor.exec( varExp );
        if ( er.isErroOuEx() )
            return new MatrizExecResult( er );
                
        if ( er.getVar() == null ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, no.getI(), ErroMSGs.VAR_ESPERADA );
            return new MatrizExecResult( erro );
        }
        
        if ( er.getVar().getTipo() != Var.MATRIZ ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, no.getI(), ErroMSGs.VAR_TIPO_MATRIZ_ESPERADO );
            return new MatrizExecResult( erro );
        }
        
        MatrizVar matvar = (MatrizVar)er.getVar();
        
        ExpMatIDs prox = matExp.getExpMatIDs();
        MatrizExecResult result = this.matriz( matExp, prox, executor, matvar );         
                
        return result;
    }
    
    public MatrizExecResult matriz( ExecNo mexp, ExpMatIDs expMatIDs, Executor executor, MatrizVar matvar ) {
        MatrizManager matrizManager = executor.getAplic().getMatrizManager();
        Codigo codigo = mexp.getCodigo();
        
        ExpMatrizIndice[] matIndices = expMatIDs.getIndices();

        int el_i = 0;
        int el_j = 0;                    
        
        MatrizIndice[] indices = {};
        
        if( matIndices.length == 1 ) {                        
            Indice id1 = this.indice( executor, matIndices[0] );
            if ( id1.er != null )
                return new MatrizExecResult( id1.er );
            if ( id1.erro != null )
                return new MatrizExecResult( id1.erro );
            
            el_j = id1.indice;
                        
            indices = new MatrizIndice[] {
                new MatrizIndice( el_j, matIndices[0].getPos() )
            };
        } else if ( matIndices.length > 1 ) {
            Indice id1 = this.indice( executor, matIndices[0] );
            if ( id1.er != null )
                return new MatrizExecResult( id1.er );
            if ( id1.erro != null )
                return new MatrizExecResult( id1.erro );
            
            Indice id2 = this.indice( executor, matIndices[1] );
            if ( id2.er != null )
                return new MatrizExecResult( id2.er );
            if ( id2.erro != null )
                return new MatrizExecResult( id2.erro );
            
            el_i = id1.indice;
            el_j = id2.indice;                
            
            indices = new MatrizIndice[] {
                new MatrizIndice( el_i, matIndices[0].getPos() ),
                new MatrizIndice( el_j, matIndices[1].getPos() ) 
            };
        }
                                
        MatrizELResult result = matrizManager.get( codigo, matvar, indices, mexp.getI() );        
        Var el = result.getEL();
        
        if ( el != null ) {
            expMatIDs = expMatIDs.getProx();
            if ( expMatIDs != null ) {
                if ( el.getTipo() != Var.MATRIZ ) {
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, mexp.getI(), ErroMSGs.VAR_TIPO_MATRIZ_ESPERADO );
                    return new MatrizExecResult( erro );
                }

                MatrizVar matvar2 = (MatrizVar)el;

                MatrizExecResult er = this.matriz( mexp, expMatIDs, executor, matvar2 );
                if ( er.isErroOuEx() )
                    return er;

                matvar = matvar2;
                el = er.getVar();
                el_i = er.getMatI();
                el_j = er.getMatJ();
            }
        }
                
        MatrizAlterador alterador = new MatrizAlterador( matvar, el_i, el_j, matrizManager.getMatrizUtil() );        
        return new MatrizExecResult( el, el_i, el_j, alterador );        
    }
    
    private Indice indice( Executor executor, ExpMatrizIndice expMID ) {
        if ( expMID.getTipo() == ExpMatrizIndice.VETOR ) {
            return new Indice( ArrayVO.VETOR );
        } else {
            ExecResult er = executor.exec( expMID.getExp() );
            if ( er.isErroOuEx() )
                return new Indice( er );
                        
            if ( er.getVar() == null ) {
                CodigoErro erro = new CodigoErro( this.getClass(), expMID.getExp().getCodigo(), expMID.getPos(), ErroMSGs.MATRIZ_INDICE_VALIDO_ESPERADO );
                return new Indice( erro );
            }
                        
            if ( er.getVar().getTipo() != Var.REAL ) {
                CodigoErro erro = new CodigoErro( this.getClass(), expMID.getExp().getCodigo(), expMID.getPos(), ErroMSGs.MATRIZ_INDICE_VALIDO_ESPERADO );
                return new Indice( erro );
            }
            
            int id = (int) ((RealVar)er.getVar()).getValor();            
            return new Indice( id );
        }
    }
    
    class Indice {
        int indice;
        Erro erro;
        ExecResult er;
        
        Indice( Erro e ) {
            erro = e;
        }
        
        Indice( int id ) {
            indice = id;
        }
        
        Indice( ExecResult r ) {
            er = r;
        }
        
    }
    
}
