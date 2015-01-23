package model;
import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import databeans.Position;

public class PositionDAO  extends GenericDAO<Position> {
	public PositionDAO(String tableName, ConnectionPool pool)
			throws DAOException {
		super(Position.class, tableName, pool);
	}
	public void create(Position newPosition) throws RollbackException {
		create(newPosition);
	}
	/*
	 * Given a customer id, return the positions he have
	 */
	public Position[] getPositions(int customer_id) throws RollbackException {
		Position[] list = match(MatchArg.equals("customer_id", customer_id));
		return list;
	}
	/*
	 * Given a new position whose share is different from what in the database table, 
	 * while customer id and fund id remain the same
	 * equals to : Update Position set share = newValue where costomer_id and fund_id keep same
	 */
	public void update(Position newPosition) throws RollbackException {
		update(newPosition);
	}
}
