
from request_manager import Request


class EmptyDatabases:
    def __init__(self):
        self.request_manager = Request("http://localhost:8080", "TOKEN")
        self.all_freelancers = self.request_manager.req_get("/rest/freelancer")
        self.deleted_freelancers = 0
        self.deleted_tasks = 0

    def remove_freelancers(self):

        print("removing freelancers...")
        for freelancer in self.all_freelancers:
            resp = self.request_manager.req_delete(f"/rest/freelancer/{freelancer['freelancerId']}")
            print("deleted freelancer with id: ", freelancer["freelancerId"], resp)
            self.deleted_freelancers += 1

    def remove_tasks(self):
        """ Removes unassigned tasks """

        print("removing unassigned tasks...")

        tasks = self.request_manager.req_get("/rest/task/all")

        for task in tasks:
            resp = self.request_manager.req_delete(f"/rest/task/{task['taskId']}")
            print("deleted task with id: ", task["taskId"], resp)
            self.deleted_tasks += 1

    def main(self):
        self.remove_freelancers()
        self.remove_tasks()

        print("DELETED FREELANCERS: ", self.deleted_freelancers)
        print("DELETED TASKS: ", self.deleted_tasks)


if __name__ == "__main__":
    ed = EmptyDatabases()
    ed.main()
