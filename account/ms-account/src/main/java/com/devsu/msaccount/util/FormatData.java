package com.devsu.msaccount.util;

import com.devsu.msaccount.dto.AccountDTO;
import com.devsu.msaccount.dto.GeneralResponse;
import com.devsu.msaccount.dto.Response;
import com.devsu.msaccount.entity.Cuenta;
import com.devsu.msaccount.entity.Movimiento;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * CLase que se encarga de formatear la data de DTO y Entity
 */
public class FormatData {
    private static FormatData formatData;
    public FormatData(){}

    public static FormatData getInstance() {
        if (formatData == null) { // Primera verificación sin bloqueo
            synchronized (FormatData.class) { // Bloqueo para hilos concurrentes
                if (formatData == null) { // Segunda verificación
                    formatData = new FormatData();
                }
            }
        }
        return formatData;
    }

    /**
     * Metodo que se encarga de pasar la data del AccountDTO a Account
     * @param account
     * @param accountDTO
     * @return
     */
    public Cuenta fromAccountDTOToAccount(Cuenta account, AccountDTO accountDTO){
        account.setClienteId(accountDTO.getClienteId());
        account.setNumeroCuenta(accountDTO.getNumeroCuenta());
        account.setTipoCuenta(accountDTO.getTipoCuenta());
        account.setSaldoInicial(accountDTO.getSaldoInicial());
        account.setSaldoActual(accountDTO.getSaldoActual());
        account.setEstado(accountDTO.getEstado());
        account.setId(accountDTO.getId());
        return account;
    }

    /**
     * Netodo que se encarga de pasar la data de Entity Acount a AccountDTO
     * @param account
     * @return
     */
    public AccountDTO fromAccountToAccountDTO(Cuenta account){
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setNumeroCuenta(account.getNumeroCuenta());
        accountDTO.setTipoCuenta(account.getTipoCuenta());
        accountDTO.setSaldoInicial(account.getSaldoInicial());
        accountDTO.setEstado(account.getEstado());
        accountDTO.setSaldoActual(account.getSaldoActual());
        accountDTO.setClienteId(account.getClienteId());
        return accountDTO;
    }

    public List<AccountDTO> fromAccountListToAccountDTOList(List<Cuenta> accounts){
        List<AccountDTO> accountDTOList = new ArrayList<>();
        for(Cuenta account: accounts){
            accountDTOList.add(fromAccountToAccountDTO(account));
        }
        return accountDTOList;
    }


    public Response formatResponse(int code, String message, Object data){
        Response response = new Response();
        response.setCode(code);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    public List<GeneralResponse> formatResponse(List<Movimiento> movements, String clientName){
        List<GeneralResponse> movementsList = new ArrayList<>();
        for(Movimiento movement: movements){
            GeneralResponse generalResponse = new GeneralResponse();
            generalResponse.setFecha(movement.getFecha().format(DateTimeFormatter.ofPattern("dd/M/yyyy")));
            generalResponse.setCliente(clientName);
            generalResponse.setNumeroCuenta(movement.getCuenta().getNumeroCuenta());
            generalResponse.setTipo(movement.getCuenta().getTipoCuenta());
            generalResponse.setSaldoIncial(movement.getCuenta().getSaldoInicial());
            generalResponse.setEstado(movement.getCuenta().getEstado());
            generalResponse.setMovimiento(movement.getValor());
            generalResponse.setSaldoDisponible(movement.getSaldo());
            movementsList.add(generalResponse);
        }
        return movementsList;
    }
}
