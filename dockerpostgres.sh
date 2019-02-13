docker pull postgres
mkdir -p $HOME/appointments-db-volume
sudo docker run --rm --name appointments-db-container -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -e POSTGRES_DB=appointmentsdb -d -p 5432:5432 -v $HOME/appointments-db-volume:/var/lib/postgresql/data postgres

