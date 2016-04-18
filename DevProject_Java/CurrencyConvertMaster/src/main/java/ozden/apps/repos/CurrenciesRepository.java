package ozden.apps.repos;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import ozden.apps.entities.Currencies;

@Transactional
public interface CurrenciesRepository  extends JpaRepository<Currencies, String>{
	public List<Currencies> findAll();
	public List<Currencies> findByCode(String code);
}
