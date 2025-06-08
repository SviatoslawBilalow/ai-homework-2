import { useState } from 'react'
import { Container, Typography, CircularProgress, Alert } from '@mui/material'
import { useUsers } from './hooks/useUsers'
import UserTable from './components/UserTable'
import UserModal from './components/UserModal'
import type { User } from './types/user'

function App() {
  const { users, loading, error, deleteUser } = useUsers()
  const [selectedUser, setSelectedUser] = useState<User | null>(null)

  const handleUserClick = (user: User) => {
    setSelectedUser(user)
  }

  const handleCloseModal = () => {
    setSelectedUser(null)
  }

  if (loading) {
    return (
      <Container sx={{ display: 'flex', justifyContent: 'center', mt: 4 }}>
        <CircularProgress />
      </Container>
    )
  }

  if (error) {
    return (
      <Container sx={{ mt: 4 }}>
        <Alert severity="error">{error}</Alert>
      </Container>
    )
  }

  return (
    <Container sx={{ py: 4 }}>
      <Typography variant="h4" component="h1" gutterBottom>
        User Management
      </Typography>
      <UserTable
        users={users}
        onUserClick={handleUserClick}
        onDeleteUser={deleteUser}
      />
      <UserModal
        user={selectedUser}
        open={!!selectedUser}
        onClose={handleCloseModal}
      />
    </Container>
  )
}

export default App
