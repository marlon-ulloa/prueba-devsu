package com.devsu.msaccount.service;

import com.devsu.msaccount.dto.AccountDTO;
import com.devsu.msaccount.dto.Response;
import com.devsu.msaccount.entity.Cuenta;
import com.devsu.msaccount.exception.ResourceNotFoundException;
import com.devsu.msaccount.repository.AccountRepository;
import com.devsu.msaccount.util.FormatData;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Clase que se encarga de reaizar la logica de negocio y sirve como intermediario entre el reposiroty y el controller
 */
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Cuenta> getAllCuentas() {
        return accountRepository.findAll();
    }

    public AccountDTO getCuentaById(Long id) {
        Cuenta account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con id " + id));
        return FormatData.getInstance().fromAccountToAccountDTO(account);
    }

    public List<AccountDTO> findByClientId(Long clientId){
        List<Cuenta> accounts= accountRepository.findByClienteId(clientId);

        return FormatData.getInstance().fromAccountListToAccountDTOList(accounts);
    }

    public Response createCuenta(AccountDTO accountDTO) {
        Cuenta newAccount = new Cuenta();
        Cuenta account = FormatData.getInstance().fromAccountDTOToAccount(newAccount, accountDTO);
        String prefix = account.getTipoCuenta().toLowerCase().compareTo("ahorros") == 0 ? "4" : "2";
        Long nexVal = account.getTipoCuenta().toLowerCase().compareTo("ahorros") == 0 ?
                accountRepository.getNextAhorrosSequenceValue() :
                accountRepository.getNextCorrienteSequenceValue();
        String numero = prefix + String.format("%05d", nexVal);
        account.setNumeroCuenta(numero);
        account.setSaldoActual(account.getSaldoInicial());
        Cuenta accountCreated = accountRepository.save(account);
        Response response = FormatData.getInstance().formatResponse(HttpStatus.CREATED.value(), "Cuenta creada exitosamente", accountCreated);
        return response;
    }

    public Response updateCuenta(Long id, AccountDTO accountDTO) {
        AccountDTO actualAccount = getCuentaById(id);
        Cuenta account = FormatData.getInstance().fromAccountDTOToAccount(new Cuenta(), actualAccount);
        account.setTipoCuenta(accountDTO.getTipoCuenta());
        account.setSaldoInicial(accountDTO.getSaldoInicial());
        account.setSaldoActual(accountDTO.getSaldoActual());
        account.setEstado(accountDTO.getEstado());
        Cuenta updatedAccount = accountRepository.save(account);
        Response response = FormatData.getInstance().formatResponse(HttpStatus.OK.value(), "Cuenta actualizada exitosamente", updatedAccount);
        return response;
    }
}
