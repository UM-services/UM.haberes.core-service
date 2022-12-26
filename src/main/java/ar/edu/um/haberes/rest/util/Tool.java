/**
 * 
 */
package ar.edu.um.haberes.rest.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import ar.edu.um.haberes.rest.util.transfer.FileInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@Slf4j
public class Tool {

	public static BigDecimal rounding(BigDecimal number, Integer decimals) {
		if (decimals < 0) {
			Long factor = 1L;
			for (int ciclo = 0; ciclo > decimals; ciclo--, factor *= 10)
				;
			return number.divide(new BigDecimal(factor), 0, RoundingMode.HALF_DOWN).multiply(new BigDecimal(factor));
		}
		return number.setScale(decimals, RoundingMode.HALF_DOWN);
	}

	public static OffsetDateTime hourAbsoluteArgentina() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.HOUR, -3);
		return calendar.getTime().toInstant().atOffset(ZoneOffset.UTC);
	}

	public static OffsetDateTime dateAbsoluteArgentina() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.HOUR, -3);
		return calendar.getTime().toInstant().atOffset(ZoneOffset.UTC);
	}

	public static OffsetDateTime dateToOffsetDateTime(Date date) {
		return date.toInstant().atOffset(ZoneOffset.UTC);
	}

	public static BigDecimal interes(BigDecimal total, BigDecimal tasa, OffsetDateTime referencia,
			OffsetDateTime fecha_calculo) {
		Double tasa_diaria = Math.pow(1.0 + tasa.doubleValue(), 1.0 / 30.0) - 1;
		Period period = Period.between(referencia.toLocalDate(), fecha_calculo.toLocalDate());
		Double factor = Math.pow(1.0 + tasa_diaria, period.getDays()) - 1;
		Double interes_double = total.doubleValue() * factor;
		return new BigDecimal(interes_double);
	}

	public static File writeFile(FileInfo fileinfo) {
		// Reescribe archivo
		String filename = fileinfo.getFilename();
		filename = filename.replace('\\', '/');
		filename = filename.substring(filename.lastIndexOf('/') + 1);
		log.debug("Filename -> " + filename);
		File file = new File(filename);
		byte[] bytes = Base64.getDecoder().decode(fileinfo.getBase64());
		try {
			OutputStream output = new FileOutputStream(file);
			output.write(bytes);
			output.close();
		} catch (FileNotFoundException e) {
			log.debug(e.getMessage());
		} catch (IOException e) {
			log.debug(e.getMessage());
		}
		return file;
	}

	private static String left(String string, Integer count) {
		if (string.length() > count) {
			return string.substring(0, count);
		}
		return string;
	}

	private static String right(String string, Integer count) {
		if (string.length() > count) {
			return string.substring(string.length() - count);
		}
		return string;
	}

	public static String number_2_text(BigDecimal value) {
		value = value.setScale(2, RoundingMode.HALF_UP);
		String[] values = String.valueOf(value).split("\\.");
		String ultimo = number_2_text_entero(new BigDecimal(values[0]));
		String centavos = " con " + values[1] + "/100";
		return (ultimo + centavos).trim();
	}

	public static String number_2_text_entero(BigDecimal value) {
		String ultimo = "";
		switch (value.toString().length()) {
		case 1:
		case 2:
		case 3:
			ultimo = tres_ultimas(value);
			break;
		case 4:
		case 5:
		case 6:
			ultimo = tres_ultimas(value.divide(new BigDecimal(1000), 0, RoundingMode.DOWN));
			if (right(ultimo, 3).equals("uno")) {
				ultimo = left(ultimo, ultimo.length() - 1);
			}
			ultimo = ultimo + " mil";
			Long value_long = value.longValue();
			Long digitos = value_long - (value_long / 1000) * 1000;
			if (digitos > 0) {
				ultimo = ultimo + " " + tres_ultimas(new BigDecimal(digitos)).trim();
			}
			break;
		case 7:
		case 8:
		case 9:
			ultimo = tres_ultimas(value.divide(new BigDecimal(1000000), 0, RoundingMode.DOWN));
			if (right(ultimo, 3).equals("uno")) {
				ultimo = left(ultimo, ultimo.length() - 1);
			}
			ultimo = ultimo + " millon";
			if (!ultimo.equals(" un millon")) {
				ultimo = ultimo + "es";
			}
			value_long = value.longValue();
			digitos = value_long - (value_long / 1000000) * 1000000;
			if (digitos / 1000 > 0) {
				ultimo = ultimo + " " + tres_ultimas(new BigDecimal(digitos / 1000)).trim();
				if (right(ultimo, 3).equals("uno")) {
					ultimo = left(ultimo, ultimo.length() - 1);
				}
				ultimo = ultimo + " mil";
			}
			if (digitos - (digitos / 1000) * 1000 > 0) {
				ultimo = ultimo + " " + tres_ultimas(new BigDecimal(digitos - (digitos / 1000) * 1000)).trim();
			}
		}
		return ultimo.trim();
	}

	private static String tres_ultimas(BigDecimal value) {
		String[] centenas = { "cien", "doscientos", "trescientos", "cuatrocientos", "quinientos", "seiscientos",
				"setecientos", "ochocientos", "novecientos" };
		String centena = "";
		String ultimo = "";

		Integer numero = value.intValue();

		if (numero > 99) {
			centena = centenas[(numero / 100) - 1];
		}
		if (numero > 100 && numero < 200) {
			centena = centena + "to";
		}
		if (numero != (numero / 100) * 100) {
			centena = centena + " ";
		}
		ultimo = dos_ultimas(numero - (numero / 100) * 100);

		return centena + ultimo;
	}

	private static String dos_ultimas(Integer value) {
		Integer largo = value.toString().trim().length();

		String unidades[] = { "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve" };
		String decena[] = { "diez", "once", "doce", "trece", "catorce", "quince", "dieciseis", "diecisiete",
				"dieciocho", "diecinueve" };
		String decenas[] = { "veint", "treinta", "cuarenta", "cincuenta", "sesenta", "setenta", "ochenta", "noventa" };
		String ultimo = "";

		switch (largo) {
		case 1:
			if (value > 0) {
				ultimo = unidades[value - 1];
			}
			break;
		case 2:
			if (value > 9 && value < 20) {
				ultimo = decena[value - 10];
			}
			if (value > 19 && value < 100) {
				ultimo = decenas[(value / 10) - 2];
				if (value == 20) {
					ultimo = ultimo + "e";
				}
				if (value > 20 && value < 30) {
					ultimo = ultimo + "i";
				}
				if (value > 30 && value < 100 && value - (value / 10) * 10 > 0) {
					ultimo = ultimo + " y ";
				}
				if (value - (value / 10) * 10 > 0) {
					ultimo = ultimo + unidades[value - (value / 10) * 10 - 1];
				}
			}
			break;
		}
		return ultimo;
	}

}
