package italo.explab.inter.exp.oo;

import italo.explab.InterAplic;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.exp.ExpRecurso;
import italo.explab.arvore.exp.OOChamada;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.util.ContadorUtil;

public class OOVarOuMatELOuFuncInter extends Inter {

    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {
        ContadorUtil contUtil = aplic.getContUtil();
        
        AnalisadorSintaticoManager asManager = aplic.getAnalisadorSintaticoManager();
        
        AnaliseResult aresult = asManager.getNovoObjetoAnalisador().analisa( codigo, i );
        if ( aresult.getErro() != null )
            return new InterResult( aresult );
                       
        InterResult ir;
        if ( aresult.getJ() > 0 ) {
            ir = aplic.getInterManager().getNovoObjetoInter().interpreta( no, base, aplic, codigo, i, i2 );
        } else { 
            aresult = asManager.getVarOuChamadaFuncAnalisador().analisa( codigo, i );
            if ( aresult.getJ() == 0 )
                return new InterResult( aresult );
                                    
            aresult = asManager.getChamadaFuncAnalisador().analisa( codigo, i );
            if ( aresult.getErro() != null )
                return new InterResult( aresult );

            if ( aresult.getJ() == 0 ) {                
                aresult = asManager.getMatELAcessoAnalisador().analisa( codigo, i );
                if ( aresult.getJ() > 0 ) { 
                    ir = aplic.getInterManager().getMatELAcessoInter().interpreta( no, base, aplic, codigo, i, i2 );
                } else {
                    ir = aplic.getInterManager().getVarInter().interpreta( no, base, aplic, codigo, i, i2 );                                        
                }
            } else {                
                ir = aplic.getInterManager().getChamadaFuncInter().interpreta( no, base, aplic, codigo, i, i2 );                
            }            
            
        }                
        
        if ( ir.getJ() == 0 ) {
            if ( ir.getErro() != null )
                return ir;
                        
            return new InterResult();
        }
        
        ExpRecurso exp = (ExpRecurso)ir.getInstrucaoOuExp();
                
        int j = ir.getJ();
        int k = j;
        
        j += contUtil.contaEsps( codigo, i+j );        
        if ( codigo.getSEGCH( i+j ) == '.' ) {
            j++;
            
            ir = this.interpreta( no, base, aplic, codigo, i+j, i2 );
            if ( ir.getJ() == 0 )
                return ir;
            
            j += ir.getJ();

            ExpRecurso chamada = (ExpRecurso)ir.getInstrucaoOuExp();                        
            
            OOChamada ooChamada = new OOChamada( exp, chamada );
            
            exp.setOOChamador( ooChamada );
            chamada.setOOChamada( ooChamada ); 
        } else {
            j = k;
        }                
                
        return new InterResult( (Exp)exp, j );
    }
    
}
