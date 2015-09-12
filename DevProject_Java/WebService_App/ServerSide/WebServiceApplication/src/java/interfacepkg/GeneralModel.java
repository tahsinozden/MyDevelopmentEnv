package interfacepkg;

import java.sql.ResultSet;

public interface GeneralModel {
	public abstract void addResultSet(ResultSet rs);
        public abstract String getInsertText();
	Object getObj();
}
