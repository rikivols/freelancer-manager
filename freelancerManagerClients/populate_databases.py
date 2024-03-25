
from request_manager import Request

from helpers import open_json


class PopulateDatabase:

    def __init__(self):
        self.request_manager = Request("http://localhost:8080", "TOKEN")
        self.freelancers = open_json("dummy_freelancers.json")
        self.tasks = open_json("dummy_tasks.json")
        self.created_freelancers = 0
        self.created_tasks = 0

    def populate_freelancers(self):

        for freelancer in self.freelancers:
            freelancer = self.request_manager.req_post("/rest/freelancer", freelancer)
            print(f"Created freelancer: {freelancer}")
            self.created_freelancers += 1

    def populate_tasks(self):
        for task in self.tasks:
            task = self.request_manager.req_post("/rest/task", task)
            print(f"Created task: {task}")
            self.created_tasks += 1

    def main(self):
        self.populate_freelancers()
        self.populate_tasks()
        print(f"Created freelancers: {self.created_freelancers}")
        print(f"Created tasks: {self.created_tasks}")


if __name__ == "__main__":
    pop = PopulateDatabase()
    pop.main()