package italo.explab.inter.leitura;

import italo.explab.ErroMSGs;
import italo.explab.InterAplic;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterTO;
import italo.explab.msg.CodigoErro;
import italo.explab.PalavrasReservadas;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.instrucao.Instrucao;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.inter.leitura.result.LeituraExceptionsInterResult;
import italo.explab.inter.leitura.result.LeituraParamsECorpoFuncInterResult;
import italo.explab.util.ContadorUtil;
import java.util.ArrayList;
import java.util.List;

public class LeituraParamsECorpoInter extends Inter {
            
    @Override
    public LeituraParamsECorpoFuncInterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {                                       
        char ch = codigo.getSEGCH( i );
        if ( ch != '(' ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i, ErroMSGs.ABRE_PARENTESES_ESPERADO );
            return new LeituraParamsECorpoFuncInterResult( erro );
        }
                
        InterManager manager = aplic.getInterManager();
        ContadorUtil contUtil = aplic.getContUtil();               
        List<String> paramsLista = new ArrayList();
        List<String> exceptionClassesLista = new ArrayList( 0 );
        List<Integer> paramsJs = new ArrayList();        
        List<Instrucao> instrucoesLista = new ArrayList();

        int j = 0;
        
        if ( codigo.getSEGCH( i+j ) != '(' ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i, ErroMSGs.ABRE_PARENTESES_ESPERADO );
            return new LeituraParamsECorpoFuncInterResult( erro );
        }
        
        j++;        
        j += contUtil.contaEsps( codigo, i+j );

        boolean achouFechaParenteses = false;
        ch = codigo.getSEGCH( i+j );
        if ( ch == ')') {
            j++;                
            achouFechaParenteses = true;
        }
        
        while ( !achouFechaParenteses && i+j < i2 ) {                                                              
            j += contUtil.contaEsps( codigo, i+j );

            int paramJ = j;
            int cont = contUtil.contaVarNomeTam( codigo, i+j );
            if ( cont == 0 ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.PARAM_DEF_ESPERADA );
                return new LeituraParamsECorpoFuncInterResult( erro );
            }

            String pnome = codigo.getCodigo().substring( i+j, i+j+cont );

            j += cont;
            j += contUtil.contaEsps( codigo, i+j );

            ch = codigo.getSEGCH( i+j );
            if ( ch == ',' || ch == ')' ) {      
                paramsLista.add( pnome );
                paramsJs.add( paramJ );

                if ( ch == ')' )
                    achouFechaParenteses = true;                                                          
                    
                j++;
            } else {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_PARENTESES_OU_VIRGULA_ESPERADO );
                return new LeituraParamsECorpoFuncInterResult( erro );
            }
        }        
        
        if ( !achouFechaParenteses ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_PARENTESES_ESPERADO );
            return new LeituraParamsECorpoFuncInterResult( erro );
        }

        j += contUtil.contaEsps( codigo, i+j );
        
        LeituraExceptionsInterResult result = (LeituraExceptionsInterResult)manager.getLeituraExceptionsInter().interpreta( no, base, aplic, codigo, i+j, i2 );
        if ( result.getJ() > 0 ) {
            exceptionClassesLista = result.getExceptionClasses();
            
            j += result.getJ();
        }
        
        ch = codigo.getSEGCH( i+j );
        if ( ch != '{' ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.ABRE_CHAVES_ESPERADO );
            return new LeituraParamsECorpoFuncInterResult( erro );
        }
                
        j++;
        int cont = contUtil.contaBlocoCodigoTam( codigo, i+j );
        if ( cont == -1 ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i2, ErroMSGs.FECHA_CHAVES_ESPERADO );
            return new LeituraParamsECorpoFuncInterResult( erro );
        }
                  
        int ii2 = i+j+cont;
        
        boolean fim = false;
        while( !fim && i+j < ii2 ) {
            j += contUtil.contaEspsEPontoEVirgulas( codigo, i+j );
            
            InterResult ir = manager.getInstrucaoInter().interpreta( no, base, aplic, codigo, i+j, ii2 );
            if ( ir.getErro() != null )
                return new LeituraParamsECorpoFuncInterResult( ir.getErro() ); 
            
            instrucoesLista.add( ir.getInstrucaoOuExp() );
            
            j += ir.getJ();
        }
        
        j += contUtil.contaEsps( codigo, i+j );
        
        if ( codigo.getSEGCH( i+j ) != '}' ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i2, ErroMSGs.FECHA_CHAVES_ESPERADO );
            return new LeituraParamsECorpoFuncInterResult( erro );
        }
        
        j++;
                    
        PalavrasReservadas prs = aplic.getPalavrasReservadas();

        int size = paramsLista.size();
        for( int k = 0; k < size; k++ ) {
            String pnome = paramsLista.get( k );
            int pj = paramsJs.get( k );

            boolean ehPR2 = prs.verificaSePalavraReservada( pnome );
            if ( ehPR2 ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+pj, ErroMSGs.EXISTE_COMO_PALAVRA_RESERVADA , pnome );
                return new LeituraParamsECorpoFuncInterResult( erro );
            }
        }
        
        String[] params = new String[ paramsLista.size() ];
        String[] exceptionClasses = new String[ exceptionClassesLista.size() ];
        Instrucao[] instrucoes = new Instrucao[ instrucoesLista.size() ];
        
        params = paramsLista.toArray( params );
        exceptionClasses = exceptionClassesLista.toArray( exceptionClasses );
        instrucoes = instrucoesLista.toArray( instrucoes );
                
        return new LeituraParamsECorpoFuncInterResult( params, exceptionClasses, instrucoes, j );
    }
        
}

