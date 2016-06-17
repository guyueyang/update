package android_app.app_update;

import com.andcup.lib.download.data.model.ResourceRepository;
import com.andcup.lib.download.service.repository.AbsRepositoryHandler;

/**
 * @author Jackoder
 * @version 2015/6/17
 */
class TaskRepository extends AbsRepositoryHandler {

    @Override
    public AbsRepositoryHandler.ResourceCreator query(ResourceRepository repository, long taskId) {
        AbsRepositoryHandler.ResourceCreator creator = ResourceCreator.creator();
        return creator;
    }
}
