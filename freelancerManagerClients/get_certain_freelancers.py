
from request_manager import Request
from datetime import datetime


class GetCertainFreelancers:

    def __init__(self):
        self.request_manager = Request("http://localhost:8080", "TOKEN")
        self.all_freelancers = self.request_manager.req_get("/rest/freelancer")
        self.all_tasks = self.request_manager.req_get("/rest/task/all")

    def freelancers_birthday_today(self):
        """ Returns freelancers who have birthday today """

        freelancers = []

        for freelancer in self.all_freelancers:
            birthday = "-".join(freelancer["birthday"].split("-")[1:])

            if birthday == datetime.now().strftime("%m-%d"):
                freelancers.append({
                    "freelancerId": freelancer["freelancerId"],
                    "name": f"{freelancer['firstName']} {freelancer['lastName']}",
                    "birthday": freelancer["birthday"]
                })

        return freelancers

    def main(self):
        res = self.freelancers_birthday_today()
        print("----------------")
        print("Freelancers with birthday today:")
        for freelancer in res:
            print("ID:", freelancer["freelancerId"], "; Name:", freelancer["name"], "; Birthday:", freelancer["birthday"])
        print("----------------")


if __name__ == "__main__":
    freelancer_picker = GetCertainFreelancers()
    freelancer_picker.main()
