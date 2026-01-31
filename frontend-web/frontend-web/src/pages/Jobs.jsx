import JobCard from "../components/JobCard";

const dummyJobs = [
  { id: 1, title: "Electrician", location: "Indore", salary: "800/day", type: "Full Time" },
  { id: 2, title: "Plumber", location: "Bhopal", salary: "700/day", type: "Part Time" },
];

export default function Jobs() {
  return (
    <div className="max-w-6xl mx-auto px-6 py-10">
      <h2 className="text-2xl font-bold mb-6">Latest Jobs</h2>

      <div className="grid sm:grid-cols-2 md:grid-cols-3 gap-6">
        {dummyJobs.map((job) => (
          <JobCard key={job.id} job={job} />
        ))}
      </div>
    </div>
  );
}
