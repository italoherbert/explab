package italo.explab.inter.instrucao;

import italo.explab.InterAplic;
import italo.explab.PalavrasReservadas;
import italo.explab.arvore.ExecNo;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.inter.cmd.AjudaCMDInter;
import italo.explab.inter.cmd.CDCMDInter;
import italo.explab.inter.cmd.ContinueCMDInter;
import italo.explab.inter.cmd.ExibaCMDInter;
import italo.explab.inter.cmd.ExibaLNCMDInter;
import italo.explab.inter.cmd.LSCMDInter;
import italo.explab.inter.cmd.LanceCMDInter;
import italo.explab.inter.cmd.LeiaLNCMDInter;
import italo.explab.inter.cmd.LimpeTelaCMDInter;
import italo.explab.inter.cmd.ListeCMDInter;
import italo.explab.inter.cmd.PareCMDInter;
import italo.explab.inter.cmd.RetorneCMDInter;
import italo.explab.inter.cmd.SairCMDInter;
import italo.explab.inter.cmd.ScriptCMDInter;
import italo.explab.inter.cmd.UseClasseCMDInter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CMDsInter extends Inter {
    
    private final Map<String, Inter> instrucoesCMDsMap = new HashMap();
    
    public CMDsInter() {        
        instrucoesCMDsMap.put( PalavrasReservadas.LIMPETELA, new LimpeTelaCMDInter());
        instrucoesCMDsMap.put( PalavrasReservadas.LISTE, new ListeCMDInter() );
        instrucoesCMDsMap.put( PalavrasReservadas.CD, new CDCMDInter() );
        instrucoesCMDsMap.put( PalavrasReservadas.LS, new LSCMDInter() );
        instrucoesCMDsMap.put( PalavrasReservadas.SCRIPT, new ScriptCMDInter() );
        instrucoesCMDsMap.put( PalavrasReservadas.AJUDA, new AjudaCMDInter() );
        instrucoesCMDsMap.put( PalavrasReservadas.USE, new UseClasseCMDInter() );                
        
        instrucoesCMDsMap.put( PalavrasReservadas.EXIBALN, new ExibaLNCMDInter() );
        instrucoesCMDsMap.put( PalavrasReservadas.EXIBA, new ExibaCMDInter() );
        instrucoesCMDsMap.put( PalavrasReservadas.RETORNE, new RetorneCMDInter() );
        instrucoesCMDsMap.put( PalavrasReservadas.CONTINUE, new ContinueCMDInter() );
        instrucoesCMDsMap.put( PalavrasReservadas.PARE, new PareCMDInter() );
        instrucoesCMDsMap.put( PalavrasReservadas.LANCE, new LanceCMDInter() );
        
        instrucoesCMDsMap.put( PalavrasReservadas.LEIALN, new LeiaLNCMDInter() );
        instrucoesCMDsMap.put( PalavrasReservadas.SAIR, new SairCMDInter() );
    }

    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2) {
        Collection<Inter> comandos = instrucoesCMDsMap.values();
        for( Inter cmd : comandos ) {            
            InterResult result = cmd.interpreta( no, base, aplic, codigo, i, i2 );
            if ( result.getJ() > 0 || result.getErro() != null )
                return result;
        }
            
        return new InterResult();
    }         
            
    public Map<String, Inter> getInstrucoesCMDsMap() {
        return instrucoesCMDsMap;
    }
    
}
