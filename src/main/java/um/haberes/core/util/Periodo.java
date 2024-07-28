/**
 * 
 */
package um.haberes.core.util;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author daniel
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Periodo {

	private Integer anho;
	private Integer mes;

	public Long toLong() {
		return (long) this.anho * 100 + this.mes;
	}

	public static Long toLong(Integer anho, Integer mes) {
		return (long) anho * 100 + mes;
	}

	public static Periodo parse(long periodo) {
		Integer anho = (int) (periodo / 100);
		Integer mes = (int) (periodo - anho * 100);
		return new Periodo(anho, mes);
	}

	public static Periodo nextMonth(Integer anho, Integer mes) {
		if (++mes == 13) {
			anho++;
			mes = 1;
		}
		return new Periodo(anho, mes);
	}

	public static Periodo nextPeriodo(long periodo) {
		Periodo p = parse(periodo);
		return nextMonth(p.getAnho(), p.getMes());
	}

	public static Periodo prevMonth(Integer anho, Integer mes) {
		if (--mes == 0) {
			anho--;
			mes = 12;
		}
		return new Periodo(anho, mes);
	}

	public static Integer diffMonth(Periodo from, Periodo to) {
		if (toLong(to.getAnho(), to.getMes()) < toLong(from.getAnho(), from.getMes())) {
			return 0;
		}
		if (from.getAnho() == to.getAnho()) {
			return to.getMes() - from.getMes();
		}
		Integer meses = (to.getAnho() - from.getAnho()) * 12;
		meses += to.getMes();
		meses -= from.getMes();
		return meses;
	}

	public static List<Periodo> makePeriodos(Long periododesde, Long periodohasta) {
		List<Periodo> periodos = new ArrayList<Periodo>();
		Long ciclo = periododesde;
		while (ciclo <= periodohasta) {
			Periodo periodo = null;
			periodos.add(periodo = Periodo.parse(ciclo));
			periodo = Periodo.nextMonth(periodo.getAnho(), periodo.getMes());
			ciclo = toLong(periodo.getAnho(), periodo.getMes());
		}
		return periodos;
	}

	public static OffsetDateTime firstDay(Integer anho, Integer mes) {
		return OffsetDateTime.of(anho, mes, 1, 0, 0, 0, 0, ZoneOffset.UTC);
	}

	public static OffsetDateTime lastDay(Integer anho, Integer mes) {
		Periodo periodo = nextMonth(anho, mes);
		return OffsetDateTime.of(periodo.getAnho(), periodo.getMes(), 1, 0, 0, 0, 0, ZoneOffset.UTC).minusDays(1);
	}

}
