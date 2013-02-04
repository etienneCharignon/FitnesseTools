package fitnesse.tools;

import static fitnesse.tools.FitnesseBeanFactory.getFitnesseBean;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import fr.....dao.DataAccessObject;
import fr.....dao.administration.G2FMUserDao;
import fr.....exception.TechnicalException;

/**
 * Gestion des contextes de transaction pour les activités d'enregistrement des données de test pour Fitnesse.
 * 
 */
public abstract class TransactionalDecisionTable {

	// oui, on sait, c'est pas super mais c'est comme ca !
	// nous permet d'accèder aux methodes CRUD du DAO sans passer par le entitymanager
	private final DataAccessObject genericDao = getFitnesseBean(G2FMUserDao.class);
	private final PlatformTransactionManager transactionManager = getFitnesseBean(PlatformTransactionManager.class);

	public abstract void executeInTransation(DataAccessObject em) throws Exception;

	public void execute() {

		TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
		transactionTemplate.execute(new TransactionCallback<Object>() {

			@Override
			public Object doInTransaction(TransactionStatus ts) {
				try {
					executeInTransation(genericDao);
				}
				catch (Exception e) {
					throw new TechnicalException(e);
				}
				return null;
			}
		});
	}
}