/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.stock.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.stock.domain.Deposito;
import eapli.base.stock.repositories.DepositoRepository;

public class RegistarDepositoController {

    private final DepositoRepository depositoRepository = PersistenceContext.repositories().deposito();

    public Deposito registarDeposito(final String codigo, final String descricao) {

        final Deposito novoDeposito = new Deposito(codigo, descricao);
        return this.depositoRepository.save(novoDeposito);

    }
}
