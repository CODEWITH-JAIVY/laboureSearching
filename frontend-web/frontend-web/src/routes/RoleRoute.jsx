import WorkerDashboard from "../pages/WorkerDashboard";
import EmployerDashboard from "../pages/EmployerDashboard";

export const roleRoutes = [
  {
    path: "/worker",
    role: "WORKER",
    element: <WorkerDashboard />,
  },
  {
    path: "/employer",
    role: "EMPLOYER",
    element: <EmployerDashboard />,
  },
];
