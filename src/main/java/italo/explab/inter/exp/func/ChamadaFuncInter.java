package italo.explab.inter.exp.func;

import italo.explab.InterAplic;
import italo.explab.PalavrasReservadas;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.arvore.ExecNo;
import italo.explab.codigo.Codigo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.exp.ExpRecurso;
import italo.explab.arvore.exp.funcoumat.FuncOuMatELExp;
import italo.explab.arvore.grupo.Grupo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.inter.exp.mat.el.MatIDs;
import italo.explab.inter.params.result.ChamadaParamInterResult;
import italo.explab.inter.params.result.ChamadaParamsInterResult;
import italo.explab.util.ContadorUtil;
import java.util.List;

public class ChamadaFuncInter extends Inter {
        
    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {        
        AnalisadorSintaticoManager asManager = aplic.getAnalisadorSintaticoManager();
                
        AnaliseResult aresult = asManager.getChamadaFuncAnalisador().analisa( codigo, i );
        if ( aresult.getJ() == 0 )
            return new InterResult( aresult );        
        
        InterManager manager = aplic.getInterManager();
        ContadorUtil contUtil = aplic.getContUtil();                 
                        
        FuncOuMatELExp fexp = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoFuncOuMatELExp( i, no, codigo );                                                                                        
        
        int j = 0;        
        int acesso = ExpRecurso.NORMAL;
        
        int cont = contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.ESTE );
        int cont2 = contUtil.contaVarNomeTam( codigo, i+j );
        if ( cont == cont2 && cont > 0 ) {
            acesso = ExpRecurso.ESTE;        
        } else {
            cont = contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.SUPER );
            if ( cont == cont2 && cont > 0 )
                acesso = ExpRecurso.SUPER;                
        }                      
        
        if ( acesso == ExpRecurso.ESTE || acesso == ExpRecurso.SUPER ) {
            j += cont;
            j += contUtil.contaEsps( codigo, i+j );
            
            if ( codigo.getSEGCH( i+j ) != '.' )
                return new InterResult();               
            
            j++;
        }
        
        j += contUtil.contaEsps( codigo, i+j );
                                
        cont = contUtil.contaVarNomeTam( codigo, i+j );
        if ( cont == 0 )
            return new InterResult();
                        
        String fnome = codigo.getCodigo().substring( i+j, i+j+cont );
                                                            
        j += cont;
        j += contUtil.contaEsps( codigo, i+j );
                
        ChamadaParamsInterResult result = (ChamadaParamsInterResult)manager.getChamadaParamsInter().interpreta( fexp, base, aplic, codigo, i+j, i2 );
        if ( result.getJ() == 0 )
            return new InterResult( result );
                               
        j += result.getJ();
        
        List<ChamadaParamInterResult> params = result.getParams();                    
                        
        int k = j;
        k += contUtil.contaEsps( codigo, i+k );
        
        if ( codigo.getSEGCH( i+k ) == '(' ) {
            j = k;
           
            MatIDs expMatIDs = manager.getMatELAcessoInter().indices( fexp, aplic, codigo, i+j, i2 );
            if ( expMatIDs.getErro() != null )
                return new InterResult( expMatIDs.getErro() );
            
            fexp.setExpMatIDs( expMatIDs.getExpMatIDs() ); 
            
            j += expMatIDs.getJ();
        }
        
        boolean transposta = false;
        
        k = j;
        k += contUtil.contaEsps( codigo, i+k );
        
        if ( codigo.getSEGCH( i+k ) == '\'' ) {
            transposta = true;
            j = k+1;
        }
                        
        Exp[] parametros = new Exp[ params.size() ];
        for( int kk = 0; kk < parametros.length; kk++ )
            parametros[ kk ] = (Exp)params.get( kk ).getValorResult().getInstrucaoOuExp();                                    
                        
        fexp.setNome( fnome );
        fexp.setParams( parametros );        
        fexp.setAcesso( acesso );
        fexp.setTransposta( transposta ); 
                
        return new InterResult( fexp, j );
    }
    
}
