package ru.innopolis.course3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import ru.innopolis.course3.models.BaseModel;

import java.util.List;

/**
 * @author Danil Popov
 */
@Component
public abstract class BaseService<T extends BaseModel> {

    protected TransactionTemplate template;

    @Autowired
    @Qualifier("transactionTemplate")
    public void setTemplate(TransactionTemplate template) {
        this.template = template;
    }

    protected abstract void addNew(T o);
    protected abstract void removeById(int id, long version);
    protected abstract void update(T o);
    protected abstract T getById(int id);
    protected abstract List<T> getAll();

    public void addNewModelTransactionally(T o) {
        template.execute(new TransactionCallback<Void>() {
            public Void doInTransaction(TransactionStatus txStatus) {
                try {
                    addNew(o);
                } catch (RuntimeException e) {
                    txStatus.setRollbackOnly();
                    throw e;
                }
                return null;
            }
        });
    }

    public void removeModelTransactionally(int id, long version) {
        template.execute(new TransactionCallback<Void>() {
            public Void doInTransaction(TransactionStatus txStatus) {
                try {
                    removeById(id, version);
                } catch (RuntimeException e) {
                    txStatus.setRollbackOnly();
                    throw e;
                }
                return null;
            }
        });
    }

    public T getByIdTransactionally(int id) {
        return template.execute(new TransactionCallback<T>() {
            public T doInTransaction(TransactionStatus txStatus) {
                T model = null;
                try {
                    model = getById(id);
                } catch (RuntimeException e) {
                    txStatus.setRollbackOnly();
                    throw e;
                }
                return model;
            }
        });
    }

    public void updateTransactionally(T o) {
        template.execute(new TransactionCallback<Void>() {
            public Void doInTransaction(TransactionStatus txStatus) {
                try {
                    update(o);
                } catch (RuntimeException e) {
                    txStatus.setRollbackOnly();
                    throw e;
                }
                return null;
            }
        });
    }

    public List<T> getAllTransactionally() {
        return template.execute(new TransactionCallback<List<T>>() {
            public List<T> doInTransaction(TransactionStatus txStatus) {
                List<T> models = null;
                try {
                    models = getAll();
                } catch (RuntimeException e) {
                    txStatus.setRollbackOnly();
                    throw e;
                }
                return models;
            }
        });
    }
}
