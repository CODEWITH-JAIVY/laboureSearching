export default function WorkerCard({ worker }) {
  return (
    <div className="bg-white p-5 rounded-xl shadow">
      <h3 className="font-semibold">{worker.name}</h3>
      <p className="text-sm text-gray-600">
        {worker.skill} • {worker.city}
      </p>

      <button className="mt-4 w-full bg-blue-600 text-white py-2 rounded">
        Contact
      </button>
    </div>
  );
}
