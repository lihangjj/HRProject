package dao.impl;

import dao.AbstractDAOImpl;
import dao.ILevelDAO;
import vo.Level;

import java.sql.Connection;
import java.util.List;

public class LevelDAOImpl extends AbstractDAOImpl<Integer, Level> implements ILevelDAO {
    public LevelDAOImpl(Connection conn) throws Exception {
        super(conn, new Level());
    }


    @Override
    public Level findByTitle(String title) throws Exception {
        bufSelectAll();
        buf.append(" where title=?");
        pre = conn.prepareStatement(buf.toString());
        pre.setString(1, title);
        res = pre.executeQuery();
        Level level = null;
        while (res.next()) {
            level = new Level();
            voSet(level);
        }
        return level;
    }

    @Override
    public Level findByLosal(Double losal) throws Exception {
        bufSelectAll();
        buf.append(" where losal="+losal);
        pre = conn.prepareStatement(buf.toString());
        res = pre.executeQuery();
        Level level = null;
        while (res.next()) {
            level = new Level();
            voSet(level);
        }
        return level;
    }

    @Override
    public Level findByTitleAndlevid(String title, Integer levid) throws Exception {
        bufSelectAll();
        buf.append(" where title=? and levid=" + levid);
        pre = conn.prepareStatement(buf.toString());
        pre.setString(1, title);
        res = pre.executeQuery();
        Level level = null;
        while (res.next()) {
            level = new Level();
            voSet(level);
        }
        return level;
    }


}
