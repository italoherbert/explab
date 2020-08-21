package italo.explab.inter.cmd;

import italo.explab.ErroMSGs;
import italo.explab.InterAplic;
import italo.explab.InterConfig;
import italo.explab.codigo.Codigo;
import italo.explab.PalavrasReservadas;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.string.node.StrValor;
import italo.explab.arvore.cmd.node.Use;
import italo.explab.arvore.cmd.node.use.UseListener;
import italo.explab.arvore.cmd.node.use.UseResult;
import italo.explab.inter.Inter;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.util.ArquivoUtil;
import java.io.FileNotFoundException;
import java.io.IOException;
import italo.explab.msg.CodigoErro;
import italo.explab.recursos.ClasseManager;
import italo.explab.util.ContadorUtil;
import italo.explab.var.StringVar;

public class UseClasseCMDInter extends Inter implements UseListener {

    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {                                        
        ContadorUtil contUtil = aplic.getContUtil();

        int cont = contUtil.contaTextoValor( codigo, i, PalavrasReservadas.USE );
        if ( cont == 0 )
            return new InterResult();
                                            
        Use use = aplic.getExecutor().getExecManager().getExecNoFactory().getCMDFactory().novoUse( i, no, codigo );

        int j = cont;
        
        int jj = contUtil.contaEsps( codigo, i+j );        
        if ( jj == 0 && i+j < i2 )
            return new InterResult();
                
        j += jj;
        
        int arqNomeJ = j;
            
        cont = contUtil.contaClasseOuPacoteNomeTam( codigo, i+j );
        if ( cont == 0 ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.CLASSE_NOME_ESPERADO );
            return new InterResult( erro );
        }
        
        String useArqNome = codigo.getCodigo().substring( i+j, i+j+cont );                              

        j += cont;
        
        StrValor strval = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoStrValor( i+arqNomeJ, use, codigo );
        strval.setValor( new StringVar( useArqNome ) );         
                
        use.setUseArqNomeExp( strval );
        use.setUseListener( this ); 
                
        return new InterResult( use, j );
    }
    
    @Override
    public UseResult interpretaClasses( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, String useArqNome, int arqNomeI ) {
        InterManager manager = aplic.getInterManager();
        InterConfig config = aplic.getConfig();
        ClasseManager classeManager = aplic.getClasseManager();
        ArquivoUtil arquivoUtil = aplic.getArquivoUtil();
        
        //if ( !classeManager.verificaSeUseJaProcessado( useArqNome ) ) {
            try {   
                int k = useArqNome.lastIndexOf( '.' );
                if ( k == -1 )
                    k = 0;
                String arquivoNome = useArqNome.substring( k ) + config.getClasseArqEXT();
                String caminho = useArqNome.replaceAll( "\\.", "/" ) + config.getClasseArqEXT();
                if ( config.getDiretorioCorrente() != null ) {
                    String barra = ( config.getDiretorioCorrente().endsWith( "/" ) ? "" : "/" );
                    caminho = config.getDiretorioCorrente() + barra + caminho;                            
                }
                
                String codCompleto = arquivoUtil.ler( caminho ); 
                Codigo codigoCompleto = new Codigo( aplic, codCompleto, arquivoNome );                                                   
                Codigo classesCodigo = codigoCompleto.codigoSemComentarios();                
                
                int len = classesCodigo.getCodlen();
                InterResult lclassesNo = manager.getLeituraClassesPacoteInter().interpreta( no, base, aplic, classesCodigo, 0, len );

                if ( lclassesNo.getJ() == 0 ) { 
                    if ( lclassesNo.getErro() != null )
                        return new UseResult( lclassesNo.getErro() );
                    
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, arqNomeI, ErroMSGs.ARQ_ERRO_ENCONTRADO_NO_CODIGO, useArqNome );
                    return new UseResult( erro );
                } else {
                    classeManager.addUseArqNome( useArqNome );
                }

            } catch ( FileNotFoundException ex ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, arqNomeI, ErroMSGs.ARQ_NAO_ENCONTRADO, useArqNome ); 
                return new UseResult( erro );
            } catch ( IOException ex ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, arqNomeI, ErroMSGs.ARQ_ERRO_LEITURA, useArqNome ); 
                return new UseResult( erro );
            }                    
        //}                   

        return new UseResult();                    
    }
    
}
