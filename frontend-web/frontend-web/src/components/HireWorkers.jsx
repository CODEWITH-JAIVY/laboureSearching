import { useEffect, useState } from "react";
import { searchWorkers } from "../services/api";
import WorkerCard from "../components/WorkerCard";

export default function HireWorkers() {
  const [workers, setWorkers] = useState([]);

  useEffect(() => {
    searchWorkers({}).then((res) => setWorkers(res.data));
  }, []);

  return (
    <div className="max-w-6xl mx-auto px-6 py-10">
      <h2 className="text-2xl font-bold mb-6">Hire Workers</h2>

      <div className="grid sm:grid-cols-2 md:grid-cols-3 gap-6">
        {workers.map((w) => (
          <WorkerCard key={w.id} worker={w} />
        ))}
      </div>
    </div>
  );
}
