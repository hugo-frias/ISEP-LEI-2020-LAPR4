package eapli.base.spm;

import eapli.base.comunicacao.domain.Mensagem;
import eapli.base.comunicacao.domain.NotificacaoErroProcessamento;
import eapli.base.comunicacao.repositories.NotificacaoErroRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;

public class CriacaoNotificacoesErroService {
    
    private final NotificacaoErroRepository erroRepo = PersistenceContext.repositories().notificacoesErro();
    
    public void criarNotificacoesErro(String m, Mensagem msg) {
        NotificacaoErroProcessamento notificacao = new NotificacaoErroProcessamento(m,msg);
        erroRepo.save(notificacao);
    }
    
}

