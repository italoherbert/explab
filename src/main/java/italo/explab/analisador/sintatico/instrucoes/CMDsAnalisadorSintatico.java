package italo.explab.analisador.sintatico.instrucoes;

import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.analisador.sintatico.cmd.AjudaAnalisadorSintatico;
import italo.explab.analisador.sintatico.cmd.CDAnalisadorSintatico;
import italo.explab.analisador.sintatico.cmd.ContinueAnalisadorSintatico;
import italo.explab.analisador.sintatico.cmd.ExibaAnalisadorSintatico;
import italo.explab.analisador.sintatico.cmd.ExibaLNAnalisadorSintatico;
import italo.explab.analisador.sintatico.cmd.LSAnalisadorSintatico;
import italo.explab.analisador.sintatico.cmd.LanceAnalisadorSintatico;
import italo.explab.analisador.sintatico.cmd.LeiaLNAnalisadorSintatico;
import italo.explab.analisador.sintatico.cmd.ListeAnalisadorSintatico;
import italo.explab.analisador.sintatico.cmd.PareAnalisadorSintatico;
import italo.explab.analisador.sintatico.cmd.RetorneAnalisadorSintatico;
import italo.explab.analisador.sintatico.cmd.SairAnalisadorSintatico;
import italo.explab.analisador.sintatico.cmd.ScriptAnalisadorSintatico;
import italo.explab.analisador.sintatico.cmd.UseAnalisadorSintatico;
import italo.explab.codigo.Codigo;
import java.util.ArrayList;
import java.util.List;

public class CMDsAnalisadorSintatico implements AnalisadorSintatico {
    
    private final List<AnalisadorSintatico> analisadores = new ArrayList();

    private final AnalisadorSintatico listeAnalisador;
    private final AnalisadorSintatico cdAnalisador;
    private final AnalisadorSintatico lsAnalisador;
    private final AnalisadorSintatico scriptAnalisador;
    private final AnalisadorSintatico ajudaAnalisador;
    private final AnalisadorSintatico sairAnalisador;
    private final AnalisadorSintatico useAnalisador;
    
    private final AnalisadorSintatico exibalnAnalisador;
    private final AnalisadorSintatico exibaAnalisador;
    private final AnalisadorSintatico leialnAnalisador;
    private final AnalisadorSintatico retorneAnalisador;
    private final AnalisadorSintatico continueAnalisador;
    private final AnalisadorSintatico pareAnalisador;
    private final AnalisadorSintatico lanceAnalisador;
    
    public CMDsAnalisadorSintatico( AnalisadorSintaticoManager manager ) {        
        analisadores.add( listeAnalisador = new ListeAnalisadorSintatico( manager ) );
        analisadores.add( cdAnalisador = new CDAnalisadorSintatico( manager ) );
        analisadores.add( lsAnalisador = new LSAnalisadorSintatico( manager ) );
        analisadores.add( scriptAnalisador = new ScriptAnalisadorSintatico( manager ) );
        analisadores.add( ajudaAnalisador = new AjudaAnalisadorSintatico( manager ) );
        analisadores.add( sairAnalisador = new SairAnalisadorSintatico( manager ) );        
        analisadores.add( useAnalisador = new UseAnalisadorSintatico( manager ) );                
        
        analisadores.add( exibalnAnalisador = new ExibaLNAnalisadorSintatico( manager ) );
        analisadores.add( exibaAnalisador = new ExibaAnalisadorSintatico( manager ) );
        analisadores.add( leialnAnalisador = new LeiaLNAnalisadorSintatico( manager ) );
        analisadores.add( retorneAnalisador = new RetorneAnalisadorSintatico( manager ) );
        analisadores.add( continueAnalisador = new ContinueAnalisadorSintatico( manager ) );
        analisadores.add( pareAnalisador = new PareAnalisadorSintatico( manager ) );
        analisadores.add( lanceAnalisador = new LanceAnalisadorSintatico( manager ) );
    }    
    
    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {
        for( AnalisadorSintatico analisador : analisadores ) {
            AnaliseResult result = analisador.analisa( codigo, i );
            if ( result.getJ() > 0 || result.getErro() != null )
                return result;
        }
        
        return new AnaliseResult();
    }

    public List<AnalisadorSintatico> getAnalisadores() {
        return analisadores;
    }

    public AnalisadorSintatico getListeAnalisador() {
        return listeAnalisador;
    }

    public AnalisadorSintatico getCdAnalisador() {
        return cdAnalisador;
    }

    public AnalisadorSintatico getLsAnalisador() {
        return lsAnalisador;
    }

    public AnalisadorSintatico getScriptAnalisador() {
        return scriptAnalisador;
    }

    public AnalisadorSintatico getAjudaAnalisador() {
        return ajudaAnalisador;
    }

    public AnalisadorSintatico getSairAnalisador() {
        return sairAnalisador;
    }

    public AnalisadorSintatico getUseAnalisador() {
        return useAnalisador;
    }

    public AnalisadorSintatico getExibalnAnalisador() {
        return exibalnAnalisador;
    }

    public AnalisadorSintatico getExibaAnalisador() {
        return exibaAnalisador;
    }

    public AnalisadorSintatico getLeialnAnalisador() {
        return leialnAnalisador;
    }

    public AnalisadorSintatico getRetorneAnalisador() {
        return retorneAnalisador;
    }

    public AnalisadorSintatico getContinueAnalisador() {
        return continueAnalisador;
    }

    public AnalisadorSintatico getPareAnalisador() {
        return pareAnalisador;
    }

    public AnalisadorSintatico getLanceAnalisador() {
        return lanceAnalisador;
    }
    
}
