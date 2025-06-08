import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  IconButton,
  Typography,
  Box,
} from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import type { User } from '../types/user';

interface UserTableProps {
  users: User[];
  onUserClick: (user: User) => void;
  onDeleteUser: (userId: number) => void;
}

const UserTable = ({ users, onUserClick, onDeleteUser }: UserTableProps) => {
  return (
    <TableContainer component={Paper} sx={{ mt: 2 }}>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Name / Email</TableCell>
            <TableCell>Address</TableCell>
            <TableCell>Phone</TableCell>
            <TableCell>Website</TableCell>
            <TableCell>Company</TableCell>
            <TableCell>Actions</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {users.map((user) => (
            <TableRow
              key={user.id}
              hover
              onClick={() => onUserClick(user)}
              sx={{ cursor: 'pointer' }}
            >
              <TableCell>
                <Box>
                  <Typography variant="subtitle2">{user.name}</Typography>
                  <Typography variant="body2" color="text.secondary">
                    {user.email}
                  </Typography>
                </Box>
              </TableCell>
              <TableCell>
                <Typography variant="body2">
                  {user.address.street}, {user.address.city}
                </Typography>
              </TableCell>
              <TableCell>
                <Typography variant="body2">{user.phone}</Typography>
              </TableCell>
              <TableCell>
                <Typography variant="body2">{user.website}</Typography>
              </TableCell>
              <TableCell>
                <Typography variant="body2">{user.company.name}</Typography>
              </TableCell>
              <TableCell>
                <IconButton
                  onClick={(e) => {
                    e.stopPropagation();
                    onDeleteUser(user.id);
                  }}
                  size="small"
                  color="error"
                >
                  <DeleteIcon />
                </IconButton>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default UserTable; 