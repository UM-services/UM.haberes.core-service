package um.haberes.rest.service.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import um.haberes.rest.exception.NovedadException;
import um.haberes.rest.kotlin.model.internal.AfipContext;
import um.haberes.rest.kotlin.model.Control;
import um.haberes.rest.kotlin.model.Item;
import um.haberes.rest.kotlin.model.Liquidacion;
import um.haberes.rest.kotlin.model.Novedad;
import um.haberes.rest.service.ItemService;
import um.haberes.rest.service.NovedadService;
import um.haberes.rest.service.facade.MakeLiquidacionService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AfipContextService {

    private final ItemService itemService;

    private final NovedadService novedadService;

    private final MakeLiquidacionService makeLiquidacionService;

    @Autowired
    public AfipContextService(ItemService itemService, NovedadService novedadService, MakeLiquidacionService makeLiquidacionService) {
        this.itemService = itemService;
        this.novedadService = novedadService;
        this.makeLiquidacionService = makeLiquidacionService;
    }

    public AfipContext makeByLegajo(Liquidacion liquidacion, Control control) {

        int afipZona = 0;
        int afipHijos = 0;
        int afipEsposa = 0;
        int afipRegimen = 0;
        int afipActividad = 0;
        int afipCondicion = 0;
        int afipSituacion = 0;
        int afipAdherentes = 0;
        int afipSiniestrado = 0;
        int afipTipoEmpleado = 0;
        int afipDiasTrabajados = 0;
        int afipModeloContratacion = 0;
        int afipReduccion = 0;
        int afipTipoEmpresa = 0;
        long afipObraSocial = 0;
        BigDecimal afipCapitalLRT = BigDecimal.ZERO;
        BigDecimal remuneracionImponible1 = BigDecimal.ZERO;
        BigDecimal remuneracionImponible2 = BigDecimal.ZERO;
        BigDecimal remuneracionImponible3 = BigDecimal.ZERO;
        BigDecimal remuneracionImponible4 = BigDecimal.ZERO;
        BigDecimal remuneracionImponible5 = BigDecimal.ZERO;
        BigDecimal minimoObraSocial = BigDecimal.ZERO;
        BigDecimal porcentajeObraSocialEmpleado = BigDecimal.ZERO;
        BigDecimal porcentajeObraSocialPatronal = BigDecimal.ZERO;
        BigDecimal coeficiente = BigDecimal.ZERO;
        BigDecimal importeBruto = BigDecimal.ZERO;
        BigDecimal importeRemunerativo = BigDecimal.ZERO;
        BigDecimal importeNoRemunerativo = BigDecimal.ZERO;
        BigDecimal incrementoSalarial = BigDecimal.ZERO;
        BigDecimal afipSAC = BigDecimal.ZERO;
        BigDecimal afipExcedenteOS = BigDecimal.ZERO;
        BigDecimal afipExcedenteSS = BigDecimal.ZERO;
        BigDecimal afipRemunerativo01 = BigDecimal.ZERO;
        BigDecimal afipRemunerativo02 = BigDecimal.ZERO;
        BigDecimal afipRemunerativo03 = BigDecimal.ZERO;
        BigDecimal afipRemunerativo04 = BigDecimal.ZERO;
        BigDecimal afipRemunerativo05 = BigDecimal.ZERO;
        BigDecimal afipRemunerativo08 = BigDecimal.ZERO;
        BigDecimal afipRemunerativo10 = BigDecimal.ZERO;
        BigDecimal afipRemunerativo11 = BigDecimal.ZERO;
        BigDecimal afipNoRemunerativo = BigDecimal.ZERO;
        BigDecimal afipImporteDetraer = BigDecimal.ZERO;
        BigDecimal afipSueldoAdicional = BigDecimal.ZERO;
        BigDecimal afipSalarioFamiliar = BigDecimal.ZERO;
        BigDecimal afipAporteVoluntario = BigDecimal.ZERO;
        BigDecimal afipAporteAdicionalSS = BigDecimal.ZERO;
        BigDecimal afipAporteAdicionalOS = BigDecimal.ZERO;
        BigDecimal afipRemuneracionTotal = BigDecimal.ZERO;
        BigDecimal afipBaseCalculoDiferencialAportesOSyFSR = BigDecimal.ZERO;
        BigDecimal afipBaseCalculoDiferencialOSyFSR = BigDecimal.ZERO;

        String afipLocalidad = "";

        remuneracionImponible1 = control.getMaximo1sijp();
        remuneracionImponible2 = control.getMaximo2sijp();
        remuneracionImponible3 = control.getMaximo3sijp();
        remuneracionImponible4 = control.getMaximo4sijp();
        remuneracionImponible5 = control.getMaximo5sijp();
        minimoObraSocial = control.getMinimoAporte();
        porcentajeObraSocialEmpleado = control.getOsociaem();
        porcentajeObraSocialPatronal = control.getOsocipat();
        int mesLiquidacion = control.getFechaHasta().getMonthValue();

        coeficiente = BigDecimal.ONE;
        if (mesLiquidacion == 6 || mesLiquidacion == 12) {
            coeficiente = new BigDecimal("1.5");
        }

        Long legajoId = liquidacion.getPersona().getLegajoId();
        Integer anho = liquidacion.getAnho();
        Integer mes = liquidacion.getMes();

        Map<Integer, Item> items = itemService.findAllByLegajo(legajoId, anho, mes).stream().collect(Collectors.toMap(Item::getCodigoId, item -> item));

        // Calcula Bruto
        if (items.containsKey(96)) {
            importeRemunerativo = items.get(96).getImporte();
        }
        // No remunerativos
        if (items.containsKey(97)) {
            importeNoRemunerativo = items.get(97).getImporte();
        }
        importeBruto = importeRemunerativo.add(importeNoRemunerativo);
        afipRemuneracionTotal = importeBruto;
        // Calcula SAC
        if (items.containsKey(3)) {
            afipSAC = items.get(3).getImporte();
        }
        afipSueldoAdicional = importeRemunerativo.subtract(afipSAC);
        // Calcula Salario Familiar
        Integer[] codigosSalarioFamiliar = {60, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44};
        for (Integer codigoIdSalarioFamiliar : codigosSalarioFamiliar) {
            if (items.containsKey(codigoIdSalarioFamiliar)) {
                afipSalarioFamiliar = afipSalarioFamiliar.add(items.get(codigoIdSalarioFamiliar).getImporte());
            }
        }
        // Calculo Incremento Salarial
        if (items.containsKey(51)) {
            incrementoSalarial = items.get(51).getImporte();
        }
        // Calculo Resto
        afipAporteVoluntario = BigDecimal.ZERO;
        afipNoRemunerativo = importeNoRemunerativo;
        afipRemunerativo11 = BigDecimal.ZERO;
        afipAporteAdicionalOS = BigDecimal.ZERO;
        afipExcedenteSS = BigDecimal.ZERO;
        afipExcedenteOS = BigDecimal.ZERO;

        afipRemunerativo01 = importeRemunerativo;
        if (afipRemuneracionTotal.compareTo(remuneracionImponible1.multiply(coeficiente)) > 0) {
            afipRemunerativo01 = remuneracionImponible1.multiply(coeficiente);
        }

        afipRemunerativo02 = importeRemunerativo;

        afipImporteDetraer = control.getMinimoAporte();
        BigDecimal mitad = control.getMinimoAporte().divide(new BigDecimal(2), 2, RoundingMode.HALF_UP);
        if (afipRemunerativo02.subtract(BigDecimal.valueOf(7003.68)).compareTo(mitad) > 0) {
            afipImporteDetraer = BigDecimal.valueOf(7003.68);
        } else {
            afipImporteDetraer = afipRemunerativo02.subtract(mitad);
        }
        if (afipImporteDetraer.compareTo(BigDecimal.ZERO) < 0) {
            afipImporteDetraer = BigDecimal.ZERO;
        }
        afipRemunerativo10 = afipRemunerativo02.subtract(afipImporteDetraer);
        if (afipImporteDetraer.compareTo(BigDecimal.ZERO) == 0) {
            afipRemunerativo10 = BigDecimal.ZERO;
        }

        afipRemunerativo03 = importeRemunerativo;
        afipRemunerativo04 = afipRemunerativo01;
        afipRemunerativo05 = importeRemunerativo;
        if (afipRemuneracionTotal.compareTo(remuneracionImponible5.multiply(coeficiente)) > 0) {
            afipRemunerativo05 = remuneracionImponible5.multiply(coeficiente);
        }
        afipRemunerativo08 = afipRemunerativo01;

        int diasNoTrabajados = 0;
        try {
            Novedad novedad = novedadService.findByUniqueWithoutDependencia(legajoId, anho, mes, 18);
            diasNoTrabajados = novedad.getImporte().intValue();
        } catch (NovedadException e) {
            diasNoTrabajados = 0;
        }
        afipDiasTrabajados = 30 - diasNoTrabajados;
        afipEsposa = 0;
        afipHijos = 0;
        afipSituacion = liquidacion.getPersona().getSituacionAfip();
        if (liquidacion.getPersona().getEstado() == 9) {
            afipSituacion = 1;
        }
        afipCondicion = liquidacion.getPersona().getEstadoAfip();
        afipModeloContratacion = liquidacion.getPersona().getModeloContratacionAfip();
        afipActividad = liquidacion.getPersona().getActividadAfip();
        afipZona = liquidacion.getPersona().getLocalidadAfip();
        afipAporteAdicionalSS = BigDecimal.ZERO;
        afipRegimen = 1;
        afipObraSocial = 0;
        if (liquidacion.getPersona().getObraSocial() != null) {
            afipObraSocial = liquidacion.getPersona().getObraSocial();
        }
        if (afipObraSocial == 0) {
            afipAporteAdicionalOS = BigDecimal.ZERO;
        }
        afipAdherentes = 0;
        afipLocalidad = "Mendoza-Gran Mendoza";
        afipSiniestrado = 0;
        afipReduccion = 0;
        afipCapitalLRT = BigDecimal.ZERO;
        afipTipoEmpleado = 1;

        afipTipoEmpresa = 1;
        if (makeLiquidacionService.evaluateOnlyETEC(items)) {
            afipTipoEmpresa = 7;
        }

//        afipBaseCalculoDiferencialAportesOSyFSR = control.getMinimoAporte().subtract(afipRemunerativo04);
//        if (afipBaseCalculoDiferencialAportesOSyFSR.compareTo(BigDecimal.ZERO) < 0) {
//            afipBaseCalculoDiferencialAportesOSyFSR = BigDecimal.ZERO;
//        }
//        afipBaseCalculoDiferencialOSyFSR = afipBaseCalculoDiferencialAportesOSyFSR;

        AfipContext afipContext = new AfipContext();
        afipContext.setLegajoId(legajoId);
        afipContext.setCuil(liquidacion.getPersona().getCuil());
        afipContext.setConyuge(afipEsposa);
        afipContext.setCantidadHijos(afipHijos);
        afipContext.setMarcaCCT(1);
        afipContext.setMarcaSCVO(1);
        afipContext.setMarcaCorrespondeReduccion(afipReduccion);
        afipContext.setTipoEmpresa(afipTipoEmpresa);
        afipContext.setTipoOperacion(0);
        afipContext.setCodigoSituacion(afipSituacion);
        afipContext.setCodigoCondicion(afipCondicion);
        afipContext.setCodigoActividad(afipActividad);
        afipContext.setCodigoModalidadContratacion(afipModeloContratacion);
        afipContext.setCodigoSiniestrado(afipSiniestrado);
        afipContext.setCodigoLocalidad(afipZona);
        afipContext.setSituacionRevista1(afipSituacion);
        afipContext.setDiaInicioSituacionRevista1(1);
        afipContext.setSituacionRevista2(1);
        afipContext.setDiaInicioSituacionRevista2(0);
        afipContext.setSituacionRevista3(1);
        afipContext.setDiaInicioSituacionRevista3(0);
        afipContext.setCantidadDiasTrabajados(afipDiasTrabajados);
        afipContext.setHorasTrabajadas(0);
        afipContext.setPorcentajeAporteAdicionalSS(BigDecimal.ZERO);
        afipContext.setContribucionTareaDiferencial(BigDecimal.ZERO);
        afipContext.setCodigoObraSocial(afipObraSocial);
        afipContext.setCantidadAdherentes(0);
        afipContext.setAporteAdicionalOS(afipAporteAdicionalOS);
        afipContext.setContribucionAdicionalOS(BigDecimal.ZERO);
        afipContext.setBaseCalculoDiferencialAportesOSyFSR(afipBaseCalculoDiferencialAportesOSyFSR);
        afipContext.setBaseCalculoDiferencialOSyFSR(afipBaseCalculoDiferencialOSyFSR);
        afipContext.setBaseCalculoDiferencialLRT(afipCapitalLRT);
        afipContext.setRemuneracionMaternidadANSeS(BigDecimal.ZERO);
        afipContext.setRemuneracionBruta(importeBruto);
        afipContext.setBaseImponible1(afipRemunerativo01);
        afipContext.setBaseImponible2(afipRemunerativo02);
        afipContext.setBaseImponible3(afipRemunerativo03);
        afipContext.setBaseImponible4(afipRemunerativo04);
        afipContext.setBaseImponible5(afipRemunerativo05);
        afipContext.setBaseImponible6(BigDecimal.ZERO);
        afipContext.setBaseImponible7(BigDecimal.ZERO);
        afipContext.setBaseImponible8(afipRemunerativo08);
        afipContext.setBaseImponible9(importeRemunerativo);
        afipContext.setBaseParaElCalculoDiferencialDeAporteDeSeguridadSocial(BigDecimal.ZERO);
        afipContext.setBaseParaElCalculoDiferencialDeContribucionesDeSeguridadSocial(BigDecimal.ZERO);
        afipContext.setBaseImponible10(afipRemunerativo10);
        afipContext.setImporteDetraer(afipImporteDetraer);

        return afipContext;
    }

}
