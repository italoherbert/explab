package italo.explab.arvore.exp.atrib;

import italo.explab.matriz.indice.MatrizIndice;
import italo.explab.ErroMSGs;
import italo.explab.InterAplic;
import italo.explab.PalavrasReservadas;
import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.VarOuFuncExecResult;
import italo.explab.codigo.Codigo;
import italo.explab.func.FuncManager;
import italo.explab.func.numerica.NFuncErro;
import italo.explab.func.numerica.NFuncResult;
import italo.explab.matriz.EsperadoTipoMatrizException;
import italo.explab.matriz.EsperadoTipoNaoMatrizException;
import italo.explab.matriz.MatVetorDIMException;
import italo.explab.matriz.MatrizELResult;
import italo.explab.matriz.MatrizManager;
import italo.explab.msg.CodigoErro;
import italo.explab.recursos.var.GlobalVarLista;
import italo.explab.var.FuncVar;
import italo.explab.var.StringVar;
import italo.explab.var.Var;
import italo.explab.var.mat.MatrizVar;
import italo.explab.var.num.NumericaVar;
import italo.explab.var_alterador.VarAlteradorException;

public class AtribExec implements Exec {
    
    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {
        Atrib atrib = (Atrib)no;
        InterAplic aplic = executor.getAplic();
        
        MatrizManager matrizManager = aplic.getMatrizManager();
        
        Codigo codigo = no.getCodigo();
        
        VarOuFuncExecResult r1 = (VarOuFuncExecResult)executor.exec( atrib.getVarExp() );
        if ( r1.isErroOuEx() )
            return r1;
            
        String varnome = r1.getVarNome();
        
        if ( varnome != null ) {
            PalavrasReservadas prs = executor.getAplic().getPalavrasReservadas();
            GlobalVarLista gVarLista = (GlobalVarLista)executor.getAplic().getGrupoRaiz().getBloco().getRecursos().getVarLista();
            
            boolean ehPR = prs.verificaSePalavraReservada( varnome );
            if ( ehPR ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, atrib.getVarExp().getI(), ErroMSGs.EXISTE_COMO_PALAVRA_RESERVADA , varnome );
                return new ExecResult( erro );
            } else if ( gVarLista.verificaSeVarConstante( varnome ) ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, atrib.getVarExp().getI(), ErroMSGs.VAR_TENTATIVA_DE_ALTERAR_CONSTANTE, varnome );                    
                return new ExecResult( erro );
            }
        }
                                
        ExecResult r2 = executor.exec( atrib.getValorExp() );        
        if ( r2.isErroOuEx() )
            return r2;                
        
        if ( r2.getExObj() != null )
            return r2;
                         
        if ( r2.getVar() == null ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, atrib.getValorExp().getI(), ErroMSGs.VALOR_NAO_NULO_ESPERADO );
            return new ExecResult( erro );
        }
        
        Var nova = null;
        switch ( atrib.getOperador() ) {
            case Atrib.IGUAL:   
                nova = r2.getVar();
                break;
        }
        
        if ( atrib.getOperador() == Atrib.MAIS_IGUAL && r1.getVar().getTipo() == Var.STRING ) {            
            StringVar s1 = (StringVar)r1.getVar();
            String s2 = aplic.getImpr().formataVar( r2.getVar() );
            nova = new StringVar( s1.getValor() + s2 );                            
        }                
        

        FuncManager fmanager = aplic.getFuncManager();
        
        if ( r2.getVar() != null )
            if ( r2.getVar().getTipo() == Var.FUNC )
                nova = (FuncVar)r2.getVar();                    
        
        if ( nova == null ) {
            Var v1 = r1.getVar();
            Var v2 = r2.getVar();
            
            if ( atrib.getOperador() == Atrib.ADD_AO_FIM ) {
                if ( v1.getTipo() == Var.MATRIZ ) {
                    MatrizVar matriz = (MatrizVar)v1;
                    MatrizIndice[] matIndices = matrizManager.calculaIndicesParaAddAoFim( matriz, atrib.getValorExp().getI() );                
                    MatrizELResult matELResult = matrizManager.set( codigo, matriz, matIndices, atrib.getVarExp().getI(), v2 );
                    if ( matELResult.getErro() != null )
                        return new ExecResult( matELResult.getErro() );

                    return new ExecResult( v2 );
                } else {
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, atrib.getVarExp().getI(), ErroMSGs.MATRIZ_TIPO_MATRIZ_ESPERADO );
                    return new ExecResult( erro );
                }             
            }        
        }
              
        if ( nova == null ) {
            if ( r1.getVar().getTipoCompativel() != Var.TC_NUMERICO ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, atrib.getVarExp().getI(), ErroMSGs.VALOR_NUMERICO_ESPERADO );
                return new ExecResult( erro );
            }
            
            if ( r2.getVar().getTipoCompativel() != Var.TC_NUMERICO ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, atrib.getValorExp().getI(), ErroMSGs.VALOR_NUMERICO_ESPERADO );
                return new ExecResult( erro );
            }
            
            NumericaVar v1 = (NumericaVar)r1.getVar();
            NumericaVar v2 = (NumericaVar)r2.getVar();
            NFuncResult result = null;
            
            switch( atrib.getOperador() ) {
                case Atrib.MAIS_IGUAL:
                    result = fmanager.getSomaFunc().exec( v1, v2 );
                    break;
                case Atrib.MENOS_IGUAL:
                    result = fmanager.getSubFunc().exec( v1, v2 );
                    break;
                case Atrib.MULT_IGUAL:
                    result = fmanager.getMultFunc().exec( v1, v2 );
                    break;
                case Atrib.DIV_IGUAL:
                    result = fmanager.getDivFunc().exec( v1, v2 );
                    break;
                case Atrib.MOD_IGUAL:
                    result = fmanager.getModFunc().exec( v1, v2 );
                    break;
                case Atrib.POT_IGUAL:
                    result = fmanager.getPotFunc().exec( v1, v2 );
                    break;                                                                            
            }            
            
            if ( result != null ) {
                if ( result.getErro() != null ) {
                    NFuncErro nerro = result.getErro();
                    int i = ( nerro.getParamIndice() == 0 ? atrib.getVarExp().getI() : atrib.getValorExp().getI() );
                    CodigoErro erro = new CodigoErro( nerro.getClass(), codigo, i, nerro.getChave(), nerro.getParams() );
                    return new ExecResult( erro );
                }
                                
                nova = result.getValor();                
            }                
        }                
                            
        try {                
            if ( nova != null ) {            
                r1.getAlterador().alteraVar( nova.nova() );                                                
                return new ExecResult( nova ); 
            } else {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, no.getI(), ErroMSGs.VAR_NAO_ALTERADA );
                return new ExecResult( erro );
            }
        } catch ( VarAlteradorException ex ) {
            if ( ex.getCause() instanceof EsperadoTipoMatrizException ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, atrib.getVarExp().getI(), ErroMSGs.MATRIZ_TIPO_MATRIZ_ESPERADO );
                return new ExecResult( erro );
            } else if ( ex.getCause() instanceof EsperadoTipoNaoMatrizException ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, atrib.getVarExp().getI(), ErroMSGs.MATRIZ_TIPO_NAO_VETOR_ESPERADO );
                return new ExecResult( erro );
            } else if ( ex.getCause() instanceof MatVetorDIMException ) {
                MatVetorDIMException vetex = (MatVetorDIMException)ex.getCause();
                String nl1 = String.valueOf( vetex.getNL1() );
                String nc1 = String.valueOf( vetex.getNC1() );
                String nl2 = String.valueOf( vetex.getNL2() );
                String nc2 = String.valueOf( vetex.getNC2() );
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, atrib.getVarExp().getI(), ErroMSGs.MATRIZ_DIM_INVALIDA, nl1, nc1, nl2, nc2 );
                return new ExecResult( erro );
            } else {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, atrib.getVarExp().getI(), ErroMSGs.VAR_NAO_ALTERADA );
                return new ExecResult( erro );
            }
        }            
    }
    
}
