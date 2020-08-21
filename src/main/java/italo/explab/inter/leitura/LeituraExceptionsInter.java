package italo.explab.inter.leitura;

import italo.explab.ErroMSGs;
import italo.explab.InterAplic;
import italo.explab.PalavrasReservadas;
import italo.explab.arvore.ExecNo;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterTO;
import italo.explab.inter.leitura.result.LeituraExceptionsInterResult;
import italo.explab.msg.CodigoErro;
import italo.explab.util.ContadorUtil;
import java.util.ArrayList;
import java.util.List;

public class LeituraExceptionsInter extends Inter {

    @Override
    public LeituraExceptionsInterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {
        ContadorUtil contUtil = aplic.getContUtil();                
                
        int j = 0;
                
        int cont = contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.TRATAR );
        if ( cont == 0 )
            return new LeituraExceptionsInterResult();
        
        j += cont;
        
        List<String> exceptionClasses = new ArrayList();

        boolean fim = false;
        while( !fim && codigo.isCHValido( i+j ) ) {
            j += contUtil.contaEsps( codigo, i+j );                        
                                    
            cont = contUtil.contaClasseOuPacoteNomeTam( codigo, i+j );
            if ( cont == 0 ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.CLASSE_DE_EXCECAO_ESPERADA );
                return new LeituraExceptionsInterResult( erro );
            }
            
            exceptionClasses.add( codigo.getCodigo().substring( i+j, i+j+cont ) );
            
            j += cont;
            j += contUtil.contaEsps( codigo, i+j );
            
            char ch = codigo.getSEGCH( i+j );
            if ( ch == '{' || ch == ',' ) {                                 
                if ( ch == '{' ) {
                    fim = true;                
                } else {
                    j++; 
                }
            }            
        }
        
        if ( fim )
            return new LeituraExceptionsInterResult( exceptionClasses, j );
        
        CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_CHAVES_ESPERADO );
        return new LeituraExceptionsInterResult( erro );
    }            
            
}
