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
		if (list.length == 0)
			return null;
		return list;
	}
	public Position getCustomerShares(int customer_id, int fund_id)
			throws RollbackException {
		Position[] position = match(MatchArg.and(
				MatchArg.equals("customer_id", customer_id),
				MatchArg.equals("fund_id", fund_id)));
		if (position.length == 0)
			return null;
		return position[0];
	}
	public void update(long value, Position pos) throws RollbackException {
		// TODO Auto-generated method stub
		try {

			if (pos.getAvailableShares() >= value) {
				Transaction.begin();
				update(pos);
				Transaction.commit();
			} else {
				throw new RollbackException(
						"Available share less than shares to be sold");
			}

		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}
	

}
