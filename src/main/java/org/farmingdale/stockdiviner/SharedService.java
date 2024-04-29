package org.farmingdale.stockdiviner;

import com.google.firebase.auth.UserRecord;
import org.farmingdale.stockdiviner.model.AnalysisType;
import org.farmingdale.stockdiviner.model.financialmodeling.FullQuoteData;

/**
 * Passes data between controllers
 */
public class SharedService {
    private static volatile SharedService instance;

    private UserRecord user;

    private FullQuoteData data;

    private AnalysisType analysisType;

    private SharedService() {
    }

    public static SharedService getInstance() {
        if (instance == null) {
            synchronized (SharedService.class) {
                if (instance == null) {
                    instance = new SharedService();
                }
            }
        }
        return instance;
    }

    public FullQuoteData getData() {
        return data;
    }

    public void setData(FullQuoteData data) {
        this.data = data;
    }

    public UserRecord getUser() {
        return user;
    }

    public void setUser(UserRecord user) {
        this.user = user;
    }

    public AnalysisType getAnalysisType() {
        return analysisType;
    }

    public void setAnalysisType(AnalysisType analysisType) {
        this.analysisType = analysisType;
    }


}
